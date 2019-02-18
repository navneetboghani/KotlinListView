package com.navneet.kotlinlistview.config

object MFAdvisoryConfig {

    var  APP_VERSION: String? = "Appname"
    var WEBURL = "http://moneyfounder.com:9090/Advisory"
    //   public static String WEBURL = "http://192.168.0.113:8080/advisory";
    var LoginURL = "/ApiLogin"
    var LogoutURL = "/ApiLogout"
    var ApiPackage = "/ApiPackage"
    var ApigetUserPackage = "/ApigetUserPackage"
    var ApiPaymentRole = "/ApiPaymentRole"
    var ApiBuyUserPackage = "/ApiBuyUserPackage"
    var ApiPaymentFile = "/ApiPaymentFile"
    var ApiCountry = "/ApiCountry"
    var ApiState = "/ApiState"
    var ApiCity = "/ApiCity"
    var ApigetUserMessage = "/ApigetUserMessage"
    var ApiUserName = "/ApiUserName"
    var ApiRegister = "/ApiRegister"
    var ApiVerifyResetcode = "/ApiVerifyResetcode"
    var ApiForgetPassword = "/ApiForgetPassword"
    var ApiResetPassword = "/ApiResetPassword"
    var ApiChangePassword = "/ApiChangePassword"
    var ApiMyProfile = "/ApiMyProfile"
    var Apicheckpackageforuser = "/Apicheckpackageforuser"
    var ApiCancelUserPackage = "/ApiCancelUserPackage"
    var ApigetUserPackagePayment = "/ApigetUserPackagePayment"
    var ApiUpdateMyProfile = "/ApiUpdateMyProfile"
    var ChatUrl = "ws://moneyfounder.com:9090/Advisory/AppChatController/%1\$s/%2\$s"
}