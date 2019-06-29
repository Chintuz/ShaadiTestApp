package com.app.shaditest.model

data class ResponseData(
    val info: Info,
    val results: ArrayList<Result>
)

data class Info(
    val page: Int,
    val results: String,
    val seed: String,
    val version: String
)

data class Result(
    val cell: String,
    val dob: Dob,
    val email: String,
    val gender: String,
    val id: Id,
    val location: Location,
    val login: Login,
    val name: Name,
    val nat: String,
    val phone: String,
    val picture: Picture,
    val registered: Registered
)

data class Login(
    val md5: String,
    val password: String,
    val salt: String,
    val sha1: String,
    val sha256: String,
    val username: String,
    val uuid: String
)

data class Registered(
    val age: Int,
    val date: String
)

data class Id(
    val name: String,
    val value: Any
)

data class Dob(
    val age: Int,
    val date: String
)

data class Name(
    val first: String,
    val last: String,
    val title: String
)

data class Location(
    val city: String,
    val coordinates: Coordinates,
    val postcode: String,
    val state: String,
    val street: String,
    val timezone: Timezone
)

data class Timezone(
    val description: String,
    val offset: String
)

data class Coordinates(
    val latitude: String,
    val longitude: String
)

data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String
)