package com.github.karlity.amiibofinder.mocks

import com.github.karlity.amiibofinder.core.models.Amiibo
import com.github.karlity.amiibofinder.core.models.AmiiboList
import com.github.karlity.amiibofinder.core.models.AmiiboSingle
import com.github.karlity.amiibofinder.core.models.Release

val exampleAmiiboList =
    AmiiboList(
        listOf(
            Amiibo(
                amiiboSeries = "Super Smash Bros.",
                character = "Mario",
                gameSeries = "Super Mario",
                head = "00000000",
                image = "https://example.com/mario_amiibo.png",
                name = "Mario",
                release =
                    Release(
                        au = "2014-11-29",
                        eu = "2014-11-28",
                        jp = "2014-12-06",
                        na = "2014-11-21",
                    ),
                tail = "00000000",
                type = "Figure",
            ),
            Amiibo(
                amiiboSeries = "Super Smash Bros.",
                character = "Link",
                gameSeries = "The Legend of Zelda",
                head = "11111111",
                image = "https://example.com/link_amiibo.png",
                name = "Link",
                release =
                    Release(
                        au = "2014-11-29",
                        eu = "2014-11-28",
                        jp = "2014-12-06",
                        na = "2014-11-21",
                    ),
                tail = "11111111",
                type = "Figure",
            ),
        ),
    )

val exampleSingleAmiibo =
    AmiiboSingle(
        Amiibo(
            amiiboSeries = "Super Smash Bros.",
            character = "Mario",
            gameSeries = "Super Mario",
            head = "00000000",
            image = "https://example.com/mario_amiibo.png",
            name = "Mario",
            release =
                Release(
                    au = "2014-11-29",
                    eu = "2014-11-28",
                    jp = "2014-12-06",
                    na = "2014-11-21",
                ),
            tail = "00000000",
            type = "Figure",
        ),
    )
