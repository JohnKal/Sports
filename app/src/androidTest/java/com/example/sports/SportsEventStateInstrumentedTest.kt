package com.example.sports

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.data.businessmodel.SportEventsModel
import com.example.sports.ui.main.sportViewer.SportViewerFragment
import com.example.sports.ui.main.sportViewer.state.RenderState
import com.example.sports.ui.main.sportViewer.state.SportsEventsState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.reflect.Type

@LargeTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class SportsEventStateInstrumentedTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var mockedResponseModel: String

    @Before
    fun setUp() {
        hiltRule.inject()

        mockedResponseModel = "[{\"sportType\":\"FOOT\",\"title\":\"SOCCER\",\"events\":[{\"eventId\":\"24456069\",\"sportId\":\"FOOT\",\"teamNames\":\"Medeama SC - Dreams FC\",\"sh\":\"\",\"startGameTime\":1668925680,\"isFav\":false},{\"eventId\":\"24439615\",\"sportId\":\"FOOT\",\"teamNames\":\"Abahani Chittagong - Mohammedan SC\",\"sh\":\"\",\"startGameTime\":1670068380,\"isFav\":false},{\"eventId\":\"24453468\",\"sportId\":\"FOOT\",\"teamNames\":\"Nigde Belediyespor - Adiyaman 1954 SK\",\"sh\":\"\",\"startGameTime\":1648816260,\"isFav\":false},{\"eventId\":\"24363881\",\"sportId\":\"FOOT\",\"teamNames\":\"Halide Edip Adivarspor - Sile Yildizspor\",\"sh\":\"\",\"startGameTime\":1649378280,\"isFav\":false},{\"eventId\":\"24376103\",\"sportId\":\"FOOT\",\"teamNames\":\"Fethiyespor - Ofspor\",\"sh\":\"\",\"startGameTime\":1660274940,\"isFav\":false},{\"eventId\":\"24427351\",\"sportId\":\"FOOT\",\"teamNames\":\"Adana Demirspor U19 - Besiktas JK U19\",\"sh\":\"\",\"startGameTime\":1658955720,\"isFav\":false},{\"eventId\":\"24427357\",\"sportId\":\"FOOT\",\"teamNames\":\"Hatayspor U19 - Goztepe U19\",\"sh\":\"\",\"startGameTime\":1656474600,\"isFav\":false},{\"eventId\":\"24456875\",\"sportId\":\"FOOT\",\"teamNames\":\"Timor-Leste U23 - Philippines U23\",\"sh\":\"\",\"startGameTime\":1657399440,\"isFav\":false},{\"eventId\":\"24402874\",\"sportId\":\"FOOT\",\"teamNames\":\"Ajax (Judge) Esports - PAOK (Kot) Esports\",\"sh\":\"\",\"startGameTime\":1667450040,\"isFav\":false},{\"eventId\":\"24402896\",\"sportId\":\"FOOT\",\"teamNames\":\"Porto (General) Esports - Celtic Glasgow (Ray) Esports\",\"sh\":\"\",\"startGameTime\":1659967140,\"isFav\":false},{\"eventId\":\"24402812\",\"sportId\":\"FOOT\",\"teamNames\":\"Celtic Glasgow (Ray) Esports - Inter Milano (Krouks) Esports\",\"sh\":\"\",\"startGameTime\":1668298860,\"isFav\":false},{\"eventId\":\"24402857\",\"sportId\":\"FOOT\",\"teamNames\":\"Dortmund (Billiot) Esports - Porto (General) Esports\",\"sh\":\"\",\"startGameTime\":1656009840,\"isFav\":false},{\"eventId\":\"24457899\",\"sportId\":\"FOOT\",\"teamNames\":\"Real Madrid (Moldovagang) Esports - Tottenham (Megapolice) Esports\",\"sh\":\"\",\"startGameTime\":1661155740,\"isFav\":false},{\"eventId\":\"24455256\",\"sportId\":\"FOOT\",\"teamNames\":\"Ogc Nice (Walker) Esports - AS Monaco (Bluefir3) Esports\",\"sh\":\"\",\"startGameTime\":1667937300,\"isFav\":false},{\"eventId\":\"24455260\",\"sportId\":\"FOOT\",\"teamNames\":\"Olympique Marseille (Void) Esports - Losc Lille (Zvatiko) Esports\",\"sh\":\"\",\"startGameTime\":1670841840,\"isFav\":false},{\"eventId\":\"24455261\",\"sportId\":\"FOOT\",\"teamNames\":\"Olympique Marseille (Void) Esports - AS Monaco (Bluefir3) Esports\",\"sh\":\"\",\"startGameTime\":1659741900,\"isFav\":false},{\"eventId\":\"24455263\",\"sportId\":\"FOOT\",\"teamNames\":\"Lyon (Fleshka77) Esports - Ogc Nice (Walker) Esports\",\"sh\":\"\",\"startGameTime\":1661507160,\"isFav\":false},{\"eventId\":\"24388345\",\"sportId\":\"FOOT\",\"teamNames\":\"Spezia (SRL) - Fiorentina (SRL)\",\"sh\":\"\",\"startGameTime\":1657373640,\"isFav\":false},{\"eventId\":\"24388298\",\"sportId\":\"FOOT\",\"teamNames\":\"RCD Mallorca (SRL) - Athletic Bilbao (SRL)\",\"sh\":\"\",\"startGameTime\":1669833000,\"isFav\":false}]},{\"sportType\":\"BASK\",\"title\":\"BASKETBALL\",\"events\":[{\"eventId\":\"24452095\",\"sportId\":\"BASK\",\"teamNames\":\"Cairns Taipans - New Zealand Breakers\",\"sh\":\"\",\"startGameTime\":1666458420,\"isFav\":false},{\"eventId\":\"24453950\",\"sportId\":\"BASK\",\"teamNames\":\"Yokohama B Corsairs - Tokyo Alvark\",\"sh\":\"\",\"startGameTime\":1651254780,\"isFav\":false},{\"eventId\":\"24456295\",\"sportId\":\"BASK\",\"teamNames\":\"Changwon LG Sakers - Wonju Dongbu Promy\",\"sh\":\"\",\"startGameTime\":1659199740,\"isFav\":false},{\"eventId\":\"24454791\",\"sportId\":\"BASK\",\"teamNames\":\"Denver Nuggets (Shag) - Phoenix Suns (Bumblebee)\",\"sh\":\"\",\"startGameTime\":1651219620,\"isFav\":false},{\"eventId\":\"24454807\",\"sportId\":\"BASK\",\"teamNames\":\"Utah Jazz (Pirs) - Dallas Mavericks (JoKeR)\",\"sh\":\"\",\"startGameTime\":1646664960,\"isFav\":false}]},{\"sportType\":\"TENN\",\"title\":\"TENNIS\",\"events\":[{\"eventId\":\"24457652\",\"sportId\":\"TENN\",\"teamNames\":\"Tomas Machac - Andrea Arnaboldi\",\"sh\":\"\",\"startGameTime\":1646543400,\"isFav\":false},{\"eventId\":\"24440974\",\"sportId\":\"TENN\",\"teamNames\":\"Caroline Garcia - Barbora Krejcikova\",\"sh\":\"\",\"startGameTime\":1672054500,\"isFav\":false},{\"eventId\":\"24440981\",\"sportId\":\"TENN\",\"teamNames\":\"Darya Kasatkina - Iga Swiatek\",\"sh\":\"\",\"startGameTime\":1667650320,\"isFav\":false},{\"eventId\":\"24454205\",\"sportId\":\"TENN\",\"teamNames\":\"Varvara Gracheva - Maryna Zanevska\",\"sh\":\"\",\"startGameTime\":1667747880,\"isFav\":false},{\"eventId\":\"24455573\",\"sportId\":\"TENN\",\"teamNames\":\"Ajla Tomljanovic - Elena Gabriela Ruse\",\"sh\":\"\",\"startGameTime\":1656111840,\"isFav\":false},{\"eventId\":\"24452348\",\"sportId\":\"TENN\",\"teamNames\":\"Evgeny Donskoy - Marc Polmans\",\"sh\":\"\",\"startGameTime\":1658571960,\"isFav\":false},{\"eventId\":\"24452349\",\"sportId\":\"TENN\",\"teamNames\":\"Arjun Kadhe - Adil Kalyanpur\",\"sh\":\"\",\"startGameTime\":1647539040,\"isFav\":false},{\"eventId\":\"24454735\",\"sportId\":\"TENN\",\"teamNames\":\"Alexander Ritschard - Evan Furness\",\"sh\":\"\",\"startGameTime\":1648491540,\"isFav\":false},{\"eventId\":\"24454036\",\"sportId\":\"TENN\",\"teamNames\":\"Hiroki Moriya - Emilio Nava\",\"sh\":\"\",\"startGameTime\":1669042140,\"isFav\":false},{\"eventId\":\"24457283\",\"sportId\":\"TENN\",\"teamNames\":\"Edas Butvilas - Adam Moundir\",\"sh\":\"\",\"startGameTime\":1653677580,\"isFav\":false},{\"eventId\":\"24457286\",\"sportId\":\"TENN\",\"teamNames\":\"Andrea Picchione - Stefano DAgostino\",\"sh\":\"\",\"startGameTime\":1658684760,\"isFav\":false},{\"eventId\":\"24465385\",\"sportId\":\"TENN\",\"teamNames\":\"Miha Vetrih - Lucas Bouquet\",\"sh\":\"\",\"startGameTime\":1650794580,\"isFav\":false},{\"eventId\":\"24457285\",\"sportId\":\"TENN\",\"teamNames\":\"Manuel Mazza - Wishaya Trongcharoenchaikul\",\"sh\":\"\",\"startGameTime\":1669707600,\"isFav\":false},{\"eventId\":\"24457292\",\"sportId\":\"TENN\",\"teamNames\":\"Tomislav Jotovski - Frane Nincevic\",\"sh\":\"\",\"startGameTime\":1657611900,\"isFav\":false},{\"eventId\":\"24456886\",\"sportId\":\"TENN\",\"teamNames\":\"Sean Hodkin - Sizya Ernest Kivanda\",\"sh\":\"\",\"startGameTime\":1670342220,\"isFav\":false},{\"eventId\":\"24456919\",\"sportId\":\"TENN\",\"teamNames\":\"Osgar OHoisin - Constantin Frantzen\",\"sh\":\"\",\"startGameTime\":1649178360,\"isFav\":false},{\"eventId\":\"24456920\",\"sportId\":\"TENN\",\"teamNames\":\"Matthew Rankin - Scott Duncan\",\"sh\":\"\",\"startGameTime\":1657418280,\"isFav\":false},{\"eventId\":\"24456921\",\"sportId\":\"TENN\",\"teamNames\":\"Ben Jones - Worovin Kumthonkittikul\",\"sh\":\"\",\"startGameTime\":1650492840,\"isFav\":false},{\"eventId\":\"24456548\",\"sportId\":\"TENN\",\"teamNames\":\"Mona Barthel - Angelina Wirges\",\"sh\":\"\",\"startGameTime\":1667533620,\"isFav\":false},{\"eventId\":\"24456551\",\"sportId\":\"TENN\",\"teamNames\":\"Vlada Koval - Jenny Duerst\",\"sh\":\"\",\"startGameTime\":1656357480,\"isFav\":false},{\"eventId\":\"24456927\",\"sportId\":\"TENN\",\"teamNames\":\"Dorka Drahota Szabo - Yasmine Mansouri\",\"sh\":\"\",\"startGameTime\":1668543300,\"isFav\":false},{\"eventId\":\"24456930\",\"sportId\":\"TENN\",\"teamNames\":\"Oleksandra Oliynykova - Ainhoa Atucha Gomez\",\"sh\":\"\",\"startGameTime\":1659613980,\"isFav\":false},{\"eventId\":\"24463472\",\"sportId\":\"TENN\",\"teamNames\":\"Dakshata Girishkumar Patel - Ishwari Matere\",\"sh\":\"\",\"startGameTime\":1660622340,\"isFav\":false},{\"eventId\":\"24457198\",\"sportId\":\"TENN\",\"teamNames\":\"Sevil Yuldasheva - Bianca Elena Barbulescu\",\"sh\":\"\",\"startGameTime\":1650489480,\"isFav\":false},{\"eventId\":\"24457182\",\"sportId\":\"TENN\",\"teamNames\":\"Nevena Sokovic - Sophie Schouten\",\"sh\":\"\",\"startGameTime\":1652853300,\"isFav\":false},{\"eventId\":\"24465847\",\"sportId\":\"TENN\",\"teamNames\":\"Borislava Botusharova - Mariya Vyshkina\",\"sh\":\"\",\"startGameTime\":1655250180,\"isFav\":false}]},{\"sportType\":\"TABL\",\"title\":\"TABLE TENNIS\",\"events\":[{\"eventId\":\"24428336\",\"sportId\":\"TABL\",\"teamNames\":\"Liang Qiu - Guilherme Teodoro\",\"sh\":\"\",\"startGameTime\":1672170240,\"isFav\":false},{\"eventId\":\"24455644\",\"sportId\":\"TABL\",\"teamNames\":\"Andrey Shmakov - Roman Alov\",\"sh\":\"\",\"startGameTime\":1665689340,\"isFav\":false},{\"eventId\":\"24455645\",\"sportId\":\"TABL\",\"teamNames\":\"Vadim Putilovsky - Pavel Medvedev\",\"sh\":\"\",\"startGameTime\":1666772220,\"isFav\":false},{\"eventId\":\"24455651\",\"sportId\":\"TABL\",\"teamNames\":\"Yaroslav Troyanov - Sergey Nikulin\",\"sh\":\"\",\"startGameTime\":1650792060,\"isFav\":false},{\"eventId\":\"24458980\",\"sportId\":\"TABL\",\"teamNames\":\"Antonin Gavlas - Youssef Abdel-Aziz\",\"sh\":\"\",\"startGameTime\":1669433640,\"isFav\":false},{\"eventId\":\"24457064\",\"sportId\":\"TABL\",\"teamNames\":\"Uriy Sandulenko - Igor Sukovatiy\",\"sh\":\"\",\"startGameTime\":1657559100,\"isFav\":false},{\"eventId\":\"24457021\",\"sportId\":\"TABL\",\"teamNames\":\"Vyacheslav Kovalenko - Dmitriy Baystryuchenko\",\"sh\":\"\",\"startGameTime\":1672169580,\"isFav\":false},{\"eventId\":\"24457060\",\"sportId\":\"TABL\",\"teamNames\":\"Evgeniy Omicinskiy - Vasyl Aksenin\",\"sh\":\"\",\"startGameTime\":1666272120,\"isFav\":false},{\"eventId\":\"24457077\",\"sportId\":\"TABL\",\"teamNames\":\"Yevhenii Holoborodko - Dmitriy Zaporozhets\",\"sh\":\"\",\"startGameTime\":1667198460,\"isFav\":false},{\"eventId\":\"24457022\",\"sportId\":\"TABL\",\"teamNames\":\"Oleg Shevchuk - Stanislav Malchuk\",\"sh\":\"\",\"startGameTime\":1668091740,\"isFav\":false}]},{\"sportType\":\"VOLL\",\"title\":\"VOLLEYBALL\",\"events\":[{\"eventId\":\"24456004\",\"sportId\":\"VOLL\",\"teamNames\":\"Burevestnik Almaty - Ushkyn Kokshetau\",\"sh\":\"\",\"startGameTime\":1666336620,\"isFav\":false},{\"eventId\":\"24455221\",\"sportId\":\"VOLL\",\"teamNames\":\"Lions - Legion Obuhovo\",\"sh\":\"\",\"startGameTime\":1658629260,\"isFav\":false}]},{\"sportType\":\"ESPS\",\"title\":\"ESPORTS\",\"events\":[{\"eventId\":\"24377275\",\"sportId\":\"ESPS\",\"teamNames\":\"Ultra Prime - FunPlus Phoenix\",\"sh\":\"\",\"startGameTime\":1657917120,\"isFav\":false},{\"eventId\":\"24451960\",\"sportId\":\"ESPS\",\"teamNames\":\"Enterprise - Anonymo Esports\",\"sh\":\"\",\"startGameTime\":1647086400,\"isFav\":false},{\"eventId\":\"24455981\",\"sportId\":\"ESPS\",\"teamNames\":\"Ninjas in Pyjamas - TSM\",\"sh\":\"\",\"startGameTime\":1649235420,\"isFav\":false},{\"eventId\":\"24455982\",\"sportId\":\"ESPS\",\"teamNames\":\"OXYGEN - FaZe Clan\",\"sh\":\"\",\"startGameTime\":1654037280,\"isFav\":false},{\"eventId\":\"24441034\",\"sportId\":\"ESPS\",\"teamNames\":\"Motivate.Trust Gaming - Yangon Galacticos\",\"sh\":\"\",\"startGameTime\":1660749900,\"isFav\":false},{\"eventId\":\"24377613\",\"sportId\":\"ESPS\",\"teamNames\":\"Nongshim RedForce Challengers - Hanwha Life Esports Challengers\",\"sh\":\"\",\"startGameTime\":1669355820,\"isFav\":false}]},{\"sportType\":\"ICEH\",\"title\":\"ICE HOCKEY\",\"events\":[{\"eventId\":\"24215297\",\"sportId\":\"ICEH\",\"teamNames\":\"Kuznetskie Medvedi U20 - Irbis Kazan U20\",\"sh\":\"\",\"startGameTime\":1665591540,\"isFav\":false}]},{\"sportType\":\"BCHV\",\"title\":\"BEACH VOLLEYBALL\",\"events\":[{\"eventId\":\"24455722\",\"sportId\":\"BCHV\",\"teamNames\":\"R.Prytuliak/V.Tyshchenko - V.Antoniuk/A.Matvieiev\",\"sh\":\"\",\"startGameTime\":1646698200,\"isFav\":false}]},{\"sportType\":\"BADM\",\"title\":\"BADMINTON\",\"events\":[{\"eventId\":\"24457974\",\"sportId\":\"BADM\",\"teamNames\":\"Harutyun Poghosyan - Hovnan Khachatryan\",\"sh\":\"\",\"startGameTime\":1653547860,\"isFav\":false},{\"eventId\":\"24457977\",\"sportId\":\"BADM\",\"teamNames\":\"Zaven Mnatsakanyan - Sargis Isahakyan\",\"sh\":\"\",\"startGameTime\":1655445900,\"isFav\":false},{\"eventId\":\"24465713\",\"sportId\":\"BADM\",\"teamNames\":\"Demi Botha / Diane Olivier - Ly Hoa Chai / Mathilde Lepetit\",\"sh\":\"\",\"startGameTime\":1654101360,\"isFav\":false}]}]"
    }

    @Test
    fun assert_the_layout_according_to_SportsEventsState_Loading_state() {
        launchSurveyListFragment(SportsEventsState.Loading)

        onView((withId(R.id.animationView))).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView((withId(R.id.sports_recycler))).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView((withId(R.id.errorView))).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun assert_the_events_according_to_SportsEventsState_SuccessDetails_state() {

        val sportsEventsModel: Type = object : TypeToken<List<SportEventsModel>?>() {}.type
        val mockedResponseModel: List<SportEventsModel> = Gson().fromJson(mockedResponseModel, sportsEventsModel)

        launchSurveyListFragment(SportsEventsState.Success(mockedResponseModel))

        onView((withId(R.id.animationView))).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView((withId(R.id.errorView))).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView((withId(R.id.sports_recycler))).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView(withId(R.id.sports_recycler))
            .check(hasItemCount(18))

        onView(RecyclerViewMatcher(R.id.sports_recycler)
            .atPositionOnView(0, R.id.tvSport))
            .check(matches(withText("SOCCER")))

        onView(RecyclerViewMatcher(R.id.sports_recycler)
            .atPositionOnView(0, R.id.icSport))
            .check(matches(withDrawable(R.drawable.ic_football)))
    }

    @Test
    fun assert_the_layout_according_to_SportsEventsState_Error_state() {

        launchSurveyListFragment(SportsEventsState.Error(401, null))

        onView((withId(R.id.animationView))).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView((withId(R.id.sports_recycler))).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView((withId(R.id.errorView))).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    private fun launchSurveyListFragment(renderState: RenderState) {
        launchFragmentInHiltContainer<SportViewerFragment> {
            (this as SportViewerFragment).renderState(
                state = renderState
            )
        }
    }
}