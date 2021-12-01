package com.me.cakeapp.data.mapper

import com.me.cakeapp.data.json.CakeDTO
import com.me.cakeapp.data.json.CakesDTO
import com.me.cakeapp.model.Cake
import javax.inject.Inject

class CakeMapper @Inject constructor() {

    fun toCakesList(cakes: CakesDTO): ArrayList<Cake> {
        val cakeList = arrayListOf<Cake>()
        for (cake in cakes)
            cakeList.add(toCake(cake))
        return cakeList
    }

    private fun toCake(cake: CakeDTO) =
        Cake(title = cake.title, image = cake.image, desc = cake.desc)
}