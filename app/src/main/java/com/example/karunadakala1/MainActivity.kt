package com.example.karunadakala1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var currentScreen by remember { mutableStateOf("home") }

            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
                    when (currentScreen) {
                        "home" -> MainDashboard(
                            onExploreClick = { currentScreen = "explore" },
                            onMapClick = { currentScreen = "map" },
                            onWorkshopClick = { currentScreen = "workshops" },
                            onEventClick = { currentScreen = "events" }
                        )
                        "explore" -> ArtExplorerScreen(onBackClick = { currentScreen = "home" })
                        "map" -> ArtisanMapScreen(onBackClick = { currentScreen = "home" })
                        "workshops" -> WorkshopScreen(onBackClick = { currentScreen = "home" })
                        "events" -> EventFeedScreen(onBackClick = { currentScreen = "home" })
                    }
                }
            }
        }
    }
}

@Composable
fun MainDashboard(onExploreClick: () -> Unit, onMapClick: () -> Unit, onWorkshopClick: () -> Unit, onEventClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Karnataka Flag
        Column(
            modifier = Modifier
                .size(width = 180.dp, height = 120.dp)
                .padding(bottom = 32.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth().weight(1f).background(Color(0xFFFFCD00)))
            Box(modifier = Modifier.fillMaxWidth().weight(1f).background(Color(0xFFC8102E)))
        }

        Text(text = "Karunada-Kala", style = MaterialTheme.typography.headlineLarge, color = Color(0xFFC8102E), modifier = Modifier.padding(bottom = 32.dp))
        DashboardButton("Explore Arts", Color(0xFFFFCD00), onClick = onExploreClick)
        DashboardButton("Artisan Map", Color(0xFFC8102E), onClick = onMapClick)
        DashboardButton("Workshops", Color(0xFFFFCD00), onClick = onWorkshopClick)
        DashboardButton("Event Feed", Color(0xFFC8102E), onClick = onEventClick)
    }
}

@Composable
fun EventFeedScreen(onBackClick: () -> Unit) {
    val events = listOf("Hampi Utsav - Nov 3", "Vairamudi Festival - Mar 20", "Pattadakal Dance Fest - Jan 15", "Mysuru Dasara - Oct 12")
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Upcoming Events", style = MaterialTheme.typography.headlineMedium, color = Color(0xFFC8102E))
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(events) { event ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDE7))) {
                    Text(text = "🗓 $event", modifier = Modifier.padding(16.dp))
                }
            }
        }
        Button(onClick = onBackClick, modifier = Modifier.fillMaxWidth()) { Text("Back to Home") }
    }
}

@Composable
fun ArtExplorerScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current
    val artForms = listOf("Yakshagana", "Kinnala Toys", "Bidriware", "Channapatna Toys")
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Karnataka Art Forms", style = MaterialTheme.typography.headlineMedium, color = Color(0xFFC8102E))
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(artForms) { art ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = art, style = MaterialTheme.typography.titleLarge)
                        Button(onClick = {
                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:9876543210"))
                            context.startActivity(intent)
                        }, modifier = Modifier.padding(top = 8.dp)) {
                            Text("📞 Call Artisan")
                        }
                    }
                }
            }
        }
        Button(onClick = onBackClick, modifier = Modifier.fillMaxWidth()) { Text("Back to Home") }
    }
}

@Composable
fun WorkshopScreen(onBackClick: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var submitted by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Register for Workshop", style = MaterialTheme.typography.headlineMedium, color = Color(0xFFC8102E))
        Spacer(modifier = Modifier.height(20.dp))
        if (!submitted) {
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Your Name") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Phone Number") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { if(name.isNotEmpty()) submitted = true }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC8102E))) { Text("Submit Registration") }
        } else {
            Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))) { Text("Success! $name, we will contact you soon.", modifier = Modifier.padding(16.dp), color = Color(0xFF2E7D32)) }
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = onBackClick, modifier = Modifier.fillMaxWidth()) { Text("Back to Home") }
    }
}

@Composable
fun ArtisanMapScreen(onBackClick: () -> Unit) {

    val context = LocalContext.current

    val locations = listOf(
        "Udupi - Yakshagana Kendra" to "geo:13.3409,74.7421?q=Yakshagana+Kendra",
        "Koppal - Kinnala Art Village" to "geo:15.3457,76.1548?q=Kinnala+Art+Village",
        "Bidar - Bidriware Cluster" to "geo:17.9104,77.5199?q=Bidriware+Cluster",
        "Channapatna - Toy Town" to "geo:12.6518,77.2067?q=Channapatna+Toy+Town"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Artisan Locations",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFFC8102E)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {

            items(locations) { (name, geoUri) ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {

                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(text = "📍 $name")

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                val intent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(geoUri)
                                )
                                context.startActivity(intent)
                            }
                        ) {
                            Text("Open in Google Maps")
                        }
                    }
                }
            }
        }

        Button(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Home")
        }
    }
}

@Composable
fun DashboardButton(text: String, color: Color, onClick: () -> Unit) {
    Button(onClick = onClick, colors = ButtonDefaults.buttonColors(containerColor = color), modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).height(60.dp)) {
        Text(text = text, color = Color.Black, style = MaterialTheme.typography.titleMedium)
    }
}
