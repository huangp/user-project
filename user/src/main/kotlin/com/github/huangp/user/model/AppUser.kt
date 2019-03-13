package com.github.huangp.user.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.github.huangp.user.util.PasswordUtil
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@Entity
@Access(AccessType.FIELD)
open class AppUser(@get: NotBlank(message = "{name.required}")
                   open var name: String? = null,
                   @get: NotBlank(message = "{username.required}")
                   @Column(unique = true)
                   open var username: String? = null,
                   @get: JsonIgnore
                   open var passwordHash: String? = null,
                   @get: NotBlank(message = "{email.required}")
                   @get: Email(message = "{email.invalid}")
                   open var email: String? = null,
                   open var active: Boolean = true,
                   @Id
                   @GeneratedValue
                   open var id: Long? = null) {

    constructor() : this(null, null, null, null, false, null)


    @Transient
    @get: NotBlank(message = "{password.required}")
    open var password: String? = null
        get() = "xxx"

    fun softDelete(): AppUser {
        return AppUser(name, username, passwordHash, email, false, id)
    }

    @PrePersist
    @PreUpdate
    fun preSave() {
        password?.let {
            passwordHash = PasswordUtil().hash(it.toCharArray())
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AppUser

        if (name != other.name) return false
        if (username != other.username) return false
        if (passwordHash != other.passwordHash) return false
        if (email != other.email) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + (username?.hashCode() ?: 0)
        result = 31 * result + (passwordHash?.hashCode() ?: 0)
        result = 31 * result + (email?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "AppUser(name=$name, username=$username, email=$email, active=$active, id=$id, passwordHash=$passwordHash)"
    }


}