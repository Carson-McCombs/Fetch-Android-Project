package com.carson_mccombs.fetch_exercise.httpClient

val errorSortingItems = Error("Error sorting items.")
val errorGettingJsonFromURL = Error("Error retrieving JSON from url.")
val errorParsingItemsFromJson = Error("Error parsing items from JSON.")
fun errorHttpStatusCode(statusCode: Int) = Error("HTTP Request Code $statusCode")