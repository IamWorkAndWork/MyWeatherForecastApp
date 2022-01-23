package com.example.myweatherforecastapp

import com.airbnb.epoxy.EpoxyDataBindingPattern

/*
 * use data binding for epoxy this will create epoxyModelClass for use in HomeUiController
 * if layout is name "epoxy_home_section_weather.xml" so we can create UI for this layout
 * by call homeSectionWeather{ ... } in HomeUiController
 * note* if we add new epoxy layout file dont't forget to rebuild to let Epoxy generate file to use in EpoxyController
 */
@EpoxyDataBindingPattern(
    rClass = R::class,
    layoutPrefix = "epoxy"
)
interface EpoxyConfig {
}