package com.example.data.businessmodel

import androidx.annotation.DrawableRes
import com.example.data.R

enum class SportType(@DrawableRes val drawableRes: Int) {
    FOOT(R.drawable.ic_football),
    BASK(R.drawable.basketball_icon),
    TENN(R.drawable.tennis_ball_icon),
    TABL(R.drawable.table_tennis_icon),
    VOLL(R.drawable.volleyball_icon),
    ESPS(R.drawable.esports_iconpng),
    ICEH(R.drawable.ice_hockey_icon),
    BCHV(R.drawable.beach_volleyball_icon),
    BADM(R.drawable.badminton_icon);
}

