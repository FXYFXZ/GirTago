package ru.fxy7ci.girtago.AstroLib

typealias TJD = Double

public class StarTimes {
    public var isSet: Boolean = false
    var Noon : TJD = 0.0 // Время полудня
    var SunRise : TJD = 0.0 //  время восхода солнца
    var SunSet : TJD = 0.0 //  время захода солнца
}