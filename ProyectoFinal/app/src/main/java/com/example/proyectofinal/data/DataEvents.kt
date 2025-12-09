package com.example.proyectofinal.data
data class Event(
    val id: String,
    val category: String,
    val title: String,
    val description: String,
    val price: String,
    val imageUrl: String,
    val text: String
)

// Esta clase se eliminara cuando UI no necesite de ella
object EventDataSource {
    val events = listOf(
        Event(
            id = "jazz_festival_1",
            category = "Música",
            title = "Festival de Jazz",
            description = "Central Park, Nueva York · 15 de julio",
            price = "$30",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuA1z92clRkUOnFYwlWU8UTh4buLTtxm0IzzcQ85gVF3YRZHcPlKXfzy3MbwSH3ns5SBcTAHlxXg8GGMqm3_s06ad7jMFIAKlJTKIJKku3ShkuVG8HeeO-eN4KSJ1Gpz7gu5oRjiO6gq8GdpUMGFR95evhpi_U-GGVBrLfVv7AV7axkOU8hue9tP4Hl9IYTUzlx2E202XpP8YGXYFGdcAbUQ8TPxlYVa3CADgWKHCUQyn6-1lxDnIWSLVPrpIWuYThm8_PbxbTQq_dJZ",
            text = "¡Déjate envolver por el ritmo y la improvisación en el Festival de Jazz más esperado del año! Disfruta de un fin de semana lleno de talento con artistas internacionales, jam sessions al aire libre y un ambiente único donde la música fluye sin fronteras. Vive la magia de los saxos, trompetas y contrabajos en una experiencia que celebra la libertad y la pasión del jazz."
        ),
        Event(
            id = "city_marathon_2",
            category = "Deporte",
            title = "Maratón de la ciudad",
            description = "Calles de la ciudad · 22 de julio",
            price = "$50",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDEl2YLn-bjRazMUMFpKWOPgj-hEFjPBoZ4u2bsAOuiEgQYSqsp8JoGbREOyErHEKiQVWHchylgUO_pYAlelDqY7onpbTxq9pNEDVXyP6KiGCBo_lCrTAV8ReaHK5bMoG2tY_zgGAKrtDBl39nvZPCHdb08u5bA8lk3g8hF9tZ3gCzdRLX3uOC5TxW6mBR1zB2lcQqYX2gfHZZ8-TeRIr98JSh0Wb6r-3dmsR6L7ZPrs5F_Xki4NW2sSeZsooWm-PxzFi9ROMpIhyxv",
            text = "¡Es momento de desafiar tus límites! Únete a la Maratón de la Ciudad y recorre las calles más emblemáticas acompañado por miles de corredores que comparten tu misma meta: llegar más lejos. Ya sea que corras por pasión, por salud o por diversión, cada paso será una victoria. ¡Prepárate para vivir la energía, el esfuerzo y la emoción en cada kilómetro!"
        ),
        Event(
            id = "opera_phantom_3",
            category = "Teatro",
            title = "El Fantasma de la Ópera",
            description = "Teatro Majestic, Nueva York · 29 de julio",
            price = "$80",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCROhCrA-vVUySeIyDoQnj0mv-ZBHM-wtr1ZTOIY0zruTNdm8YFTplu9hDSp5KLP9FnkjdXiga7gCQCm3JftR_hKG0Fv6hMNmj9EdguT8UFhzuVe1CBQRd70f8XI83q0phaINbazdWQaMke6xQ7yR10_E3ZlIxgqYY352yezzSN8thbJxPSd-jnOHhdrZf2Zmrjs01tNiB9TMkbK0CxrdU7JkseVzneDXL_gbRSr2M86HxnbAdxeVbSugv7wAcJ_7ulhgkKWxSsTt-G",
            text = "Sumérgete en la oscuridad y la belleza del clásico musical El Fantasma de la Ópera. Una puesta en escena majestuosa con impresionantes voces, vestuarios deslumbrantes y una historia de amor, misterio y obsesión que ha cautivado al mundo entero. Vive una experiencia teatral única donde cada nota resonará en tu corazón mucho después del telón final."
        ),
        Event(
            id = "movie_premiere_4",
            category = "Cine",
            title = "Estreno de película",
            description = "Cine local · 5 de agosto",
            price = "$15",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCqpkiFSPimk8qBx-HLQRh4Gn4J_GgzN2Bcc5F7nILLPmNNM5cAGHJpU3QDfDB6TerbNlqYxduY4unCbMoKQKhteOHkTaxjVN7OAALhIMaHyxdGeVbQ1C283IXxiCAYsEu3Z6SXtj2wVfepQjmJsrbWUa0zzXu-_AuzR5LNkMMgLGfhdMjdNVBZbawkD3sMzAbZPOmY9KsIxxRc_b-RUKBPK9xwavZYeIDsN7XEI0ms-2a-hVk9AapzDuNW0ZMF3tHVLiiVA9cyCmQM",
            text = "¡El momento que todos esperaban ha llegado! Acompáñanos al gran Estreno de la Película del año y sé el primero en disfrutar de una historia que te atrapará desde el primer minuto. Luces, alfombra roja, emociones y sorpresas te esperan en una noche llena de glamour y cine. ¡Ven y vive la magia del séptimo arte como solo la gran pantalla puede ofrecer!"
        ),
        Event(
            id = "modern_art_expo_5",
            category = "Arte",
            title = "Exposición de arte moderno",
            description = "Museo de Arte Moderno · 12 de agosto",
            price = "$20",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuB9mmG5_HTyaKXQZR92Zo8pJ6ahVp8Ypa-IBi22loMLQi-CrGBQKcAx_k7kteyd_ytyvpt_2vPFMQ8t5lE1dMGOG8_gM2_LS-aCl8px-rL83fEGgAecI1tS8yxLBr01PQA-l7QRg6vGhqMrEVeVoX4mJbufEWLS_9QfreaPc7N7EWcSthTjSyCzKhscDRzKDQACGk2bXkzg2VMEm-8u1YYfGRtUEDgDuRUtLZ9eaBlmnE66MyYnzSOHz_AgOOT5Bt25c4Zlmf1NioNb",
            text = "Explora la creatividad sin límites en la Exposición de Arte Moderno. Un recorrido visual por las obras más innovadoras de artistas contemporáneos que desafían la forma, el color y la percepción. Sumérgete en una experiencia multisensorial donde cada pieza te invita a reflexionar, sentir y reinterpretar el mundo que te rodea."
        ),
        Event(
            id = "indie_rock_6",
            category = "Música",
            title = "Indie Rock Festival",
            description = "Saturday, July 20, 7:00 PM",
            price = "$120",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuB9T-o1BKjYLjaz3VCL18tlGGm3xKZONJaNjj4ZpY-YQlCQ79ZjYrt0y34ZQ-ldkqPgcxPpvZ8St9NEOOsLBvR5IYLdTXLIapleIw61j-NgJkYwQiwJ6rvurLORVt2gdR_GAaKuSQevbhKx7bQP0tQmEtHTen1NiDKUf8b-jM5vkqkFdIZuCf2QsOkvV-CbY7w7SqcydY-wyVtvwgymJShFmeFqpk9rjWy95YIV2E7CxL3lGl3QNBd8DatsKbAEprrXIHYgw-ZUjz6_",
            text = "¡Vive la mejor fiesta del rock indie en Central Park! Con las electrizantes actuaciones de The Sonic Waves, Electric Echoes y The Velvet Underground, este festival promete una noche inolvidable de música, energía y puro rock 'n' roll. Prepárate para cantar a todo pulmón tus éxitos favoritos y descubrir nuevos himnos que te acompañarán mucho después de que se apague la última nota"
        )
    )

    // Función de ayuda para buscar por ID
    fun getEventById(eventId: String?): Event? {
        return events.find { it.id == eventId }
    }
}