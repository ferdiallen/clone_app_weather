package com.example.cloneopenweather.core

object CountryName {
    private val countryMap = mapOf(
        "ID" to "Indonesia",
        "US" to "United States",
        "GB" to "United Kingdom",
        "JP" to "Japan",
        "FR" to "France",
        "DE" to "Germany",
        "IN" to "India",
        "CN" to "China",
        "BR" to "Brazil",
        "CA" to "Canada",
        "AU" to "Australia",
        "SG" to "Singapore",
        "MY" to "Malaysia",
        "TH" to "Thailand",
        "PH" to "Philippines",
        "VN" to "Vietnam",
        "KR" to "South Korea",
        "IT" to "Italy",
        "ES" to "Spain",
        "NL" to "Netherlands",
        "RU" to "Russia",
        "MX" to "Mexico",
        "ZA" to "South Africa",
        "SA" to "Saudi Arabia",
        "AE" to "United Arab Emirates",
        "CH" to "Switzerland",
        "SE" to "Sweden",
        "BE" to "Belgium",
        "PT" to "Portugal",
        "NZ" to "New Zealand",
        "FI" to "Finland",
        "NO" to "Norway",
        "DK" to "Denmark",
        "IE" to "Ireland",
        "PL" to "Poland",
        "AR" to "Argentina",
        "CL" to "Chile",
        "CO" to "Colombia",
        "PE" to "Peru",
        "TR" to "Turkey",
        "EG" to "Egypt",
        "NG" to "Nigeria",
        "PK" to "Pakistan",
        "BD" to "Bangladesh"
    )
    fun getCountryName(code: String) = countryMap.getOrElse(code) {
        "Unknown"
    }
}