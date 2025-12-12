package com.example.proyectofinal.data.source

import com.example.proyectofinal.domain.model.Event
import javax.inject.Inject

class StaticEventDataSource @Inject constructor() {

    private fun getFutureTimestamp(days: Int): Long {
        return System.currentTimeMillis() + days * 24 * 60 * 60 * 1000L
    }

    fun getBaseEvents(): List<Event> {
        return listOf(
            Event(
                id = "jazz_festival_1",
                category = "Música",
                title = "Festival de Jazz",
                description = "Central Park, Nueva York",
                price = "30",
                imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuA1z92clRkUOnFYwlWU8UTh4buLTtxm0IzzcQ85gVF3YRZHcPlKXfzy3MbwSH3ns5SBcTAHlxXg8GGMqm3_s06ad7jMFIAKlJTKIJKku3ShkuVG8HeeO-eN4KSJ1Gpz7gu5oRjiO6gq8GdpUMGFR95evhpi_U-GGVBrLfVv7AV7axkOU8hue9tP4Hl9IYTUzlx2E202XpP8YGXYFGdcAbUQ8TPxlYVa3CADgWKHCUQyn6-1lxDnIWSLVPrpIWuYThm8_PbxbTQq_dJZ",
                text = "Un evento increíble de Jazz...",
                // Nuevos campos requeridos:
                dateTimestamp = getFutureTimestamp(5), // En 5 días
                isUserCreated = false,
                isInAgenda = false
            ),
            Event(
                id = "city_marathon_2",
                category = "Deporte",
                title = "Maratón de la ciudad",
                description = "Calles de la ciudad",
                price = "50",
                imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDEl2YLn-bjRazMUMFpKWOPgj-hEFjPBoZ4u2bsAOuiEgQYSqsp8JoGbREOyErHEKiQVWHchylgUO_pYAlelDqY7onpbTxq9pNEDVXyP6KiGCBo_lCrTAV8ReaHK5bMoG2tY_zgGAKrtDBl39nvZPCHdb08u5bA8lk3g8hF9tZ3gCzdRLX3uOC5TxW6mBR1zB2lcQqYX2gfHZZ8-TeRIr98JSh0Wb6r-3dmsR6L7ZPrs5F_Xki4NW2sSeZsooWm-PxzFi9ROMpIhyxv",
                text = "Corre por las calles...",
                dateTimestamp = getFutureTimestamp(-2), // Hace 2 días
                isUserCreated = false,
                isInAgenda = false
            ),
            Event(
                id = "opera_phantom_3",
                category = "Teatro",
                title = "El Fantasma de la Ópera",
                description = "Teatro Majestic",
                price = "80",
                imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCROhCrA-vVUySeIyDoQnj0mv-ZBHM-wtr1ZTOIY0zruTNdm8YFTplu9hDSp5KLP9FnkjdXiga7gCQCm3JftR_hKG0Fv6hMNmj9EdguT8UFhzuVe1CBQRd70f8XI83q0phaINbazdWQaMke6xQ7yR10_E3ZlIxgqYY352yezzSN8thbJxPSd-jnOHhdrZf2Zmrjs01tNiB9TMkbK0CxrdU7JkseVzneDXL_gbRSr2M86HxnbAdxeVbSugv7wAcJ_7ulhgkKWxSsTt-G",
                text = "Clásico musical...",
                dateTimestamp = getFutureTimestamp(10),
                isUserCreated = false,
                isInAgenda = false
            ),
            Event(
                id = "movie_premiere_4",
                category = "Cine",
                title = "Estreno de película",
                description = "Cine local",
                price = "15",
                imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCqpkiFSPimk8qBx-HLQRh4Gn4J_GgzN2Bcc5F7nILLPmNNM5cAGHJpU3QDfDB6TerbNlqYxduY4unCbMoKQKhteOHkTaxjVN7OAALhIMaHyxdGeVbQ1C283IXxiCAYsEu3Z6SXtj2wVfepQjmJsrbWUa0zzXu-_AuzR5LNkMMgLGfhdMjdNVBZbawkD3sMzAbZPOmY9KsIxxRc_b-RUKBPK9xwavZYeIDsN7XEI0ms-2a-hVk9AapzDuNW0ZMF3tHVLiiVA9cyCmQM",
                text = "Estreno del año...",
                dateTimestamp = getFutureTimestamp(1),
                isUserCreated = false,
                isInAgenda = false
            ),
            Event(
                id = "modern_art_expo_5",
                category = "Arte",
                title = "Exposición de arte",
                description = "Museo de Arte Moderno",
                price = "20",
                imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuB9mmG5_HTyaKXQZR92Zo8pJ6ahVp8Ypa-IBi22loMLQi-CrGBQKcAx_k7kteyd_ytyvpt_2vPFMQ8t5lE1dMGOG8_gM2_LS-aCl8px-rL83fEGgAecI1tS8yxLBr01PQA-l7QRg6vGhqMrEVeVoX4mJbufEWLS_9QfreaPc7N7EWcSthTjSyCzKhscDRzKDQACGk2bXkzg2VMEm-8u1YYfGRtUEDgDuRUtLZ9eaBlmnE66MyYnzSOHz_AgOOT5Bt25c4Zlmf1NioNb",
                text = "Arte contemporáneo...",
                dateTimestamp = getFutureTimestamp(15),
                isUserCreated = false,
                isInAgenda = false
            ),
            Event(
                id = "indie_rock_6",
                category = "Música",
                title = "Indie Rock Festival",
                description = "Parque Central",
                price = "120",
                imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuB9T-o1BKjYLjaz3VCL18tlGGm3xKZONJaNjj4ZpY-YQlCQ79ZjYrt0y34ZQ-ldkqPgcxPpvZ8St9NEOOsLBvR5IYLdTXLIapleIw61j-NgJkYwQiwJ6rvurLORVt2gdR_GAaKuSQevbhKx7bQP0tQmEtHTen1NiDKUf8b-jM5vkqkFdIZuCf2QsOkvV-CbY7w7SqcydY-wyVtvwgymJShFmeFqpk9rjWy95YIV2E7CxL3lGl3QNBd8DatsKbAEprrXIHYgw-ZUjz6_",
                text = "Rock toda la noche...",
                dateTimestamp = getFutureTimestamp(20),
                isUserCreated = false,
                isInAgenda = false
            )
        )
    }
}