package org.sodgutt.homesource.services

import User
import org.apache.commons.codec.digest.DigestUtils
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.sodgutt.homesource.Db.dbQuery
import org.sodgutt.homesource.UserDao
import java.util.UUID

class UserExistsSignupException : Exception("User already exists")
class LoginInput(val username: String, val password: String)

interface UserService {
    suspend fun login(loginInput: LoginInput): User?

    suspend fun signUp(user: User): UUID
    
    suspend fun doesUserExist(user: User): Boolean
}

class UserServiceImpl : UserService {
    override suspend fun login(loginInput: LoginInput): User? {
        return dbQuery {
            UserDao.select {
                (UserDao.username eq loginInput.username) and (UserDao.password eq DigestUtils.sha256Hex(loginInput.password))
            }.firstNotNullOfOrNull {
                User(
                    id = it[UserDao.id],
                    name = it[UserDao.name],
                    username = it[UserDao.username],
                    authToken = it[UserDao.authToken],
                )
            }
        }
    }

    override suspend fun doesUserExist(user: User) = dbQuery {
        UserDao.select {
            UserDao.username neq user.username
        }
    }.count() > 0

    override suspend fun signUp(user: User): UUID {
        if (doesUserExist(user)) {
            throw UserExistsSignupException()
        }
        
        val authToken = UUID.randomUUID()
        dbQuery {
            UserDao.insert {
                it[UserDao.authToken] = authToken.toString()
                it[username] = user.username
                it[password] = user.password
                it[name] = user.name
            }
        }

        return authToken
    }
}