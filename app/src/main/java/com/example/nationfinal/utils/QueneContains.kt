package com.example.nationfinal.utils

import com.example.nationfinal.R
import com.example.nationfinal.model.QueneModel

object QueneContains {
        val queue: List<QueneModel> = listOf(
            QueneModel(
                id = "1",
                name = "ДОБРО\nПОЖАЛОВАТЬ",
                text = "",
                image = R.drawable.dunk,
                line = R.drawable.first,
                textButton = "Начать"
            ),
            QueneModel(
                id = "2",
                name = "Начнем\nпутешествие",
                text = "Умная, великолепная и модная\nколлекция Изучите сейчас",
                image = R.drawable.huarach,
                line = R.drawable.second,
                textButton = "Далее"
            ),
            QueneModel(
                id = "3",
                name = "У вас есть сила,\nчтобы",
                text = "В вашей комнате много красивых и\nпривлекательных растений",
                image = R.drawable.jordan,
                line = R.drawable.third,
                textButton = "Далее"
            )
        )
}