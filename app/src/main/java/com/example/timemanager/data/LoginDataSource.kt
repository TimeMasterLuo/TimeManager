package com.example.timemanager.data

import com.example.timemanager.data.model.LoggedInUser
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            //val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
            //return Result.Success(fakeUser)
            if (username=="MikeShaw"&&password=="123456"){
                val login_info = LoggedInUser(java.util.UUID.randomUUID().toString(), username)
                return Result.Success(login_info)
            }else{
                val bad_try_info = LoggedInUser(java.util.UUID.randomUUID().toString(), "错误的用户名或密码!")
                return Result.Fail(bad_try_info)
            }


        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}