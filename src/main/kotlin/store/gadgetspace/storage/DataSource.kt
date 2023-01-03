package store.gadgetspace.storage

import store.gadgetspace.models.User

object DataSource {
    val storageService = UserStorageService()
    fun validateUser (email: String, password: String, user: User) : Boolean {
        return (user.email == email && user.password == password)
    }
}