package com.example.kartalrecord

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class NewsViewModel : ViewModel() {

    fun returnNews(): List<News> {
        return listOf(
            News(R.drawable.news1, "FUTBOL", "Beşiktaş'ta flaş transfer iddiasi!"),
            News(
                R.drawable.news2basket,
                "BASKETBOL",
                "Potanın Kartalları Türkiye Kupası'nda farklı mağlup!"
            ),
            News(
                R.drawable.news3,
                "FUTBOL",
                "Beşiktaş'ın gündemindeki stoper: Denis Vavro"
            ),
            News(
                R.drawable.news4,
                "FUTBOL",
                "Semih Kılıçsoy, Icardi’yi geride bıraktı!"
            )
        )
    }

    suspend fun fetchXMLData(urlString: String): String = withContext(Dispatchers.IO) {
        val url = URL(urlString)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connect()

        val reader = BufferedReader(InputStreamReader(connection.inputStream))
        val stringBuilder = StringBuilder()

        var line: String? = reader.readLine()
        while (line != null) {
            stringBuilder.append(line)
            line = reader.readLine()
        }

        reader.close()
        connection.disconnect()

        stringBuilder.toString()
    }

}