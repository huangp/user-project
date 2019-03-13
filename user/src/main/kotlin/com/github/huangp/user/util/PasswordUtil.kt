package com.github.huangp.user.util

import org.slf4j.LoggerFactory
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.spec.InvalidKeySpecException
import java.util.*
import java.util.regex.Pattern
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import kotlin.experimental.xor


/**
 * Hash passwords for storage, and test passwords against password tokens.
 *
 * Instances of this class can be used concurrently by multiple threads.
 *
 * @author erickson
 * @see <a href="http://stackoverflow.com/a/2861125/3474">StackOverflow</a>
 *
 * @param cost
 * the exponential computational cost of hashing a password, 0 to
 * 30
 */
class PasswordUtil(private val cost: Int = DEFAULT_COST,
                   private val random: SecureRandom = SecureRandom()) {
    /**
     * Create a password manager with a specified cost
     *
     * @param cost
     * the exponential computational cost of hashing a password, 0 to
     * 30
     */
    init {
        assert(cost >= 0 && cost <= 30, { "cost should be between 0 and 30"} )
    }

    companion object {
        /**
         * The minimum recommended cost, used by default
         */
        val DEFAULT_COST = 16
        val log = LoggerFactory.getLogger(PasswordUtil::class.java)

    }

    /**
     * Each token produced by this class uses this identifier as a prefix.
     */
    private val ID = "$31$"

    private val ALGORITHM = "PBKDF2WithHmacSHA1"

    private val SALT_SIZE = 128

    private val layout = Pattern.compile("\\$31\\$(\\d\\d?)\\$(.{43})")

    private fun iterations(cost: Int): Int {
        if (cost < 0 || cost > 30)
            throw IllegalArgumentException("cost: $cost")
        return 1 shl cost
    }

    /**
     * Hash a password for storage.
     *
     * @return a secure authentication token to be stored for later
     * authentication
     */
    fun hash(password: CharArray): String {
        val salt = ByteArray(SALT_SIZE / 8)
        random.nextBytes(salt)
        val dk = pbkdf2(password, salt, 1 shl cost)
        val hash = ByteArray(salt.size + dk.size)
        System.arraycopy(salt, 0, hash, 0, salt.size)
        System.arraycopy(dk, 0, hash, salt.size, dk.size)
        val enc = Base64.getUrlEncoder().withoutPadding()
        return ID + cost + '$' + enc.encodeToString(hash)
    }

    /**
     * Authenticate with a password and a stored password token.
     *
     * @return true if the password and token match
     */
    fun authenticate(password: CharArray, passwordHash: String): Boolean {
        val m = layout.matcher(passwordHash)
        if (!m.matches()) {
            log.debug("Invalid token format")
            return false
        }
        val iterations = iterations(Integer.parseInt(m.group(1)))
        val hash = Base64.getUrlDecoder().decode(m.group(2))
        val salt = Arrays.copyOfRange(hash, 0, SALT_SIZE / 8)
        val check = pbkdf2(password, salt, iterations)
        var zero = 0
        for (idx in check.indices)
            zero = zero or ((hash[salt.size + idx] xor check[idx]).toInt())
        return zero == 0
    }

    private fun pbkdf2(password: CharArray, salt: ByteArray, iterations: Int): ByteArray {
        val spec = PBEKeySpec(password, salt, iterations, SALT_SIZE)
        return try {
            val f = SecretKeyFactory.getInstance(ALGORITHM)
            f.generateSecret(spec).encoded
        } catch (ex: NoSuchAlgorithmException) {
            throw IllegalStateException("Missing algorithm: $ALGORITHM",
                    ex)
        } catch (ex: InvalidKeySpecException) {
            throw IllegalStateException("Invalid SecretKeyFactory", ex)
        }

    }
}