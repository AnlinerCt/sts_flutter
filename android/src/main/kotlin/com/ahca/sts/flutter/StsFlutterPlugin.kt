package com.ahca.sts.flutter

import android.app.Activity
import androidx.core.content.ContextCompat
import com.ahca.sts.STShield
import com.ahca.sts.models.*
import com.google.gson.Gson

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

/** StsFlutterPlugin */
class StsFlutterPlugin : FlutterPlugin, ActivityAware, MethodChannel.MethodCallHandler {
    private lateinit var activity: Activity

    // This static function is optional and equivalent to onAttachedToEngine. It supports the old
    // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
    // plugin registration via this function while apps migrate to use the new Android APIs
    // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
    //
    // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
    // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
    // depending on the user's project. onAttachedToEngine or registerWith must both be defined
    // in the same class.
    fun registerWith(activity: Activity, binaryMessenger: BinaryMessenger) {
        this.activity = activity
        val channel = MethodChannel(binaryMessenger, "sts_flutter")
        channel.setMethodCallHandler(this)
    }

    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "sts_flutter")
        channel.setMethodCallHandler(this)
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        activity = binding.activity
    }

    override fun onDetachedFromActivityForConfigChanges() {
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        onAttachedToActivity(binding)
    }

    override fun onDetachedFromActivity() {
    }

    override fun onMethodCall(methodCall: MethodCall, channelResult: MethodChannel.Result) {
        when (methodCall.method) {
          "init" -> {
            val baseUrl = methodCall.argument<String>("baseUrl")!!
            val stsAppKey = methodCall.argument<String>("stsAppKey")!!
            val stsSecretKey = methodCall.argument<String>("stsSecretKey")!!
            STShield.init(activity, baseUrl, stsAppKey, stsSecretKey)
            channelResult.success("")
          }
          "initUseId" -> {
            val useId = methodCall.argument<String>("useId")!!
            STShield.initUseId(activity, useId)
            channelResult.success("")
          }
          "initFace" -> {
            val id = methodCall.argument<String>("id")!!
            val fileName = methodCall.argument<String>("fileName")!!
            STShield.initFace(activity, id, fileName)
            channelResult.success("")
          }
          "initThemeColor" -> {
            val color = methodCall.argument<Int>("color")!!
            STShield.initThemeColor(activity, ContextCompat.getColor(activity, color))
            channelResult.success("")
          }
          "getDeviceID" -> {
            channelResult.success(STShield.getDeviceID(activity))
          }
          "isLocalCertExist" -> {
            channelResult.success(STShield.isLocalCertExist(activity))
          }
          "checkCert" -> {
            STShield.checkCert(activity) {
              val hashMap = HashMap<String, Any?>()
              hashMap["resultCode"] = it.resultCode
              hashMap["resultMsg"] = it.resultMsg
              channelResult.success(hashMap)
            }
          }
          "clearCert" -> {
            channelResult.success(STShield.clearCert(activity))
          }
          "applyPersonalCert" -> {
            val stsUserInfo = methodCall.argument<String>("stsUserInfo")!!
            val fromJson = Gson().fromJson(stsUserInfo, StsUserInfo::class.java)

            STShield.applyPersonalCert(activity, fromJson) {
              channelResult.success(Gson().toJson(it))
            }
          }
          "applyCompanyCert" -> {
            val stsCompanyInfo = methodCall.argument<String>("stsCompanyInfo")!!
            val fromJson = Gson().fromJson(stsCompanyInfo, StsCompanyInfo::class.java)

            STShield.applyCompanyCert(activity, fromJson) {
              channelResult.success(Gson().toJson(it))
            }
          }
          "getUntieEquipmentQRcode" -> {
            STShield.getUntieEquipmentQRcode(activity) {
              val hashMap = HashMap<String, Any?>()
              hashMap["resultCode"] = it.resultCode
              hashMap["resultMsg"] = it.resultMsg
              hashMap["qrCode"] = it.qrCode
              channelResult.success(hashMap)
            }
          }
          "untieEquipment" -> {
            val qrData = methodCall.argument<String>("qrData")!!
            STShield.untieEquipment(activity, qrData) {
              channelResult.success(Gson().toJson(it))
            }
          }
          "updatePersonalCert" -> {
            val stsUserInfo = methodCall.argument<String>("stsUserInfo")!!
            val fromJson = Gson().fromJson(stsUserInfo, StsUserInfo::class.java)

            STShield.updatePersonalCert(activity, fromJson) {
              val hashMap = HashMap<String, Any?>()
              hashMap["resultCode"] = it.resultCode
              hashMap["resultMsg"] = it.resultMsg
              channelResult.success(hashMap)
            }
          }
          "updateCompanyCert" -> {
            val stsCompanyInfo = methodCall.argument<String>("stsCompanyInfo")!!
            val fromJson = Gson().fromJson(stsCompanyInfo, StsCompanyInfo::class.java)

            STShield.updateCompanyCert(activity, fromJson) {
              val hashMap = HashMap<String, Any?>()
              hashMap["resultCode"] = it.resultCode
              hashMap["resultMsg"] = it.resultMsg
              channelResult.success(hashMap)
            }
          }
          "resetPersonalPIN" -> {
            val stsUserInfo = methodCall.argument<String>("stsUserInfo")!!
            val fromJson = Gson().fromJson(stsUserInfo, StsUserInfo::class.java)

            STShield.resetPersonalPIN(activity, fromJson) {
              val hashMap = HashMap<String, Any?>()
              hashMap["resultCode"] = it.resultCode
              hashMap["resultMsg"] = it.resultMsg
              channelResult.success(hashMap)
            }
          }
          "resetCompanyPIN" -> {
            val stsCompanyInfo = methodCall.argument<String>("stsCompanyInfo")!!
            val fromJson = Gson().fromJson(stsCompanyInfo, StsCompanyInfo::class.java)

            STShield.resetCompanyPIN(activity, fromJson) {
              val hashMap = HashMap<String, Any?>()
              hashMap["resultCode"] = it.resultCode
              hashMap["resultMsg"] = it.resultMsg
              channelResult.success(hashMap)
            }
          }
          "modifyPIN" -> {
            STShield.modifyPIN(activity) {
              val hashMap = HashMap<String, Any?>()
              hashMap["resultCode"] = it.resultCode
              hashMap["resultMsg"] = it.resultMsg
              channelResult.success(hashMap)
            }
          }
          "scanLogin" -> {
            val stsScanInfo = methodCall.argument<String>("stsScanInfo")!!
            val fromJson = Gson().fromJson(stsScanInfo, StsScanInfo::class.java)

            STShield.scanLogin(activity, fromJson) {
              val hashMap = HashMap<String, Any?>()
              hashMap["resultCode"] = it.resultCode
              hashMap["resultMsg"] = it.resultMsg
              channelResult.success(hashMap)
            }
          }
          "scanSign" -> {
            val stsScanInfo = methodCall.argument<String>("stsScanInfo")!!
            val fromJson = Gson().fromJson(stsScanInfo, StsScanInfo::class.java)

            STShield.scanSign(activity, fromJson) {
              val hashMap = HashMap<String, Any?>()
              hashMap["resultCode"] = it.resultCode
              hashMap["resultMsg"] = it.resultMsg
              channelResult.success(hashMap)
            }
          }
          "certLogin" -> {
            val data = methodCall.argument<String>("data")!!
            val dataFormat = methodCall.argument<String>("dataFormat")!!
            val dataType = methodCall.argument<String>("dataType")!!
            val pn = methodCall.argument<String>("pn")!!

            STShield.certLogin(activity, data, dataFormat, dataType, pn) {
              val hashMap = HashMap<String, Any>()
              hashMap["resultCode"] = it.resultCode
              hashMap["resultMsg"] = it.resultMsg
              hashMap["signCert"] = it.signCert
              hashMap["signData"] = it.signData
              hashMap["token"] = it.token
              channelResult.success(hashMap)
            }
          }
          "certSeal" -> {
            val pn = methodCall.argument<String>("pn")!!

            STShield.certSeal(activity, pn) {
              val hashMap = HashMap<String, Any>()
              hashMap["resultCode"] = it.resultCode
              hashMap["resultMsg"] = it.resultMsg
              hashMap["token"] = it.token
              channelResult.success(hashMap)
            }
          }
          "certSign" -> {
            val data = methodCall.argument<String>("data")!!
            val dataFormat = methodCall.argument<String>("dataFormat")!!
            val dataType = methodCall.argument<String>("dataType")!!
            val pn = methodCall.argument<String>("pn")!!

            STShield.certSign(activity, data, dataFormat, dataType, pn) {
              val hashMap = HashMap<String, Any>()
              hashMap["resultCode"] = it.resultCode
              hashMap["resultMsg"] = it.resultMsg
              hashMap["signCert"] = it.signCert
              hashMap["signData"] = it.signData
              hashMap["token"] = it.token
              channelResult.success(hashMap)
            }
          }
          "certVerifySign" -> {
            val data = methodCall.argument<String>("data")!!
            val dataFormat = methodCall.argument<String>("dataFormat")!!
            val signData = methodCall.argument<String>("signData")!!
            val dataType = methodCall.argument<String>("dataType")!!

            STShield.certVerifySign(activity, data, dataFormat, signData, dataType) {
              val hashMap = HashMap<String, Any>()
              hashMap["resultCode"] = it.resultCode
              hashMap["resultMsg"] = it.resultMsg
              channelResult.success(hashMap)
            }
          }
          "certEncrypt" -> {
            val data = methodCall.argument<String>("data")!!
            val dataFormat = methodCall.argument<String>("dataFormat")!!
            val dataType = methodCall.argument<String>("dataType")!!

            STShield.certEncrypt(activity, data, dataFormat, dataType) {
              val hashMap = HashMap<String, Any>()
              hashMap["resultCode"] = it.resultCode
              hashMap["resultMsg"] = it.resultMsg
              hashMap["encryptData"] = it.encryptData
              channelResult.success(hashMap)
            }
          }
          "certDecrypt" -> {
            val data = methodCall.argument<String>("data")!!
            val dataFormat = methodCall.argument<String>("dataFormat")!!
            val dataType = methodCall.argument<String>("dataType")!!
            val pn = methodCall.argument<String>("pn")!!

            STShield.certDecrypt(activity, data, dataFormat, dataType, pn) {
              val hashMap = HashMap<String, Any>()
              hashMap["resultCode"] = it.resultCode
              hashMap["resultMsg"] = it.resultMsg
              hashMap["decryptData"] = it.decryptData
              channelResult.success(hashMap)
            }
          }
          "changeCertStatus" -> {
            val statusType = methodCall.argument<Int>("statusType")!!

            STShield.changeCertStatus(activity, statusType) {
              val hashMap = HashMap<String, Any>()
              hashMap["resultCode"] = it.resultCode
              hashMap["resultMsg"] = it.resultMsg
              channelResult.success(hashMap)
            }
          }
          "postponeCert" -> {
            STShield.postponeCert(activity) {
              val hashMap = HashMap<String, Any>()
              hashMap["resultCode"] = it.resultCode
              hashMap["resultMsg"] = it.resultMsg
              channelResult.success(hashMap)
            }
          }
          "setPKCacheTime" -> {
            val pn = methodCall.argument<String>("pn")!!

            STShield.setPKCacheTime(activity, pn) {
              val hashMap = HashMap<String, Any>()
              hashMap["resultCode"] = it.resultCode
              hashMap["resultMsg"] = it.resultMsg
              hashMap["token"] = it.token
              channelResult.success(hashMap)
            }
          }
          "clearPKCacheTime" -> {
            val pn = methodCall.argument<String>("pn")!!

            STShield.clearPKCacheTime(activity, pn) {
              val hashMap = HashMap<String, Any>()
              hashMap["resultCode"] = it.resultCode
              hashMap["resultMsg"] = it.resultMsg
              hashMap["token"] = it.token
              channelResult.success(hashMap)
            }
          }
          "getCert" -> {
            val certType = methodCall.argument<Int>("certType")!!

            STShield.getCert(activity, certType) {
              channelResult.success(Gson().toJson(it))
            }
          }
          "getLocalCert" -> {
            val certType = methodCall.argument<Int>("certType")!!

            val localCert: GetCertResult = STShield.getLocalCert(activity, certType)
            channelResult.success(Gson().toJson(localCert))
          }
          "downloadCert" -> {
            val phoneNum = methodCall.argument<String>("phoneNum")!!
            val departmentNo = methodCall.argument<String>("departmentNo")!!
            val certType = methodCall.argument<Int>("certType")!!

            STShield.downloadCert(activity, phoneNum, departmentNo, certType) {
              channelResult.success(Gson().toJson(it))
            }
          }
          "getDepartmentNo" -> {
            STShield.getDepartmentNo(activity) {
              channelResult.success(Gson().toJson(it))
            }
          }
          "setSignImgWithDrawingBoard" -> {
            val stsSignImgSetting = methodCall.argument<String>("stsSignImgSetting")
            val fromJson: StsSignImgSetting = if (stsSignImgSetting.isNullOrEmpty()) StsSignImgSetting() else Gson().fromJson(stsSignImgSetting, StsSignImgSetting::class.java)

            STShield.setSignImgWithDrawingBoard(activity, fromJson) {
              channelResult.success(Gson().toJson(it))
            }
          }
          "setSignImgBase64Str" -> {
            val imgBase64 = methodCall.argument<String>("imgBase64")!!

            STShield.setSignImgBase64Str(activity, imgBase64) {
              channelResult.success(Gson().toJson(it))
            }
          }
          "getSignImgFromService" -> {
            STShield.getSignImgFromService(activity) {
              channelResult.success(Gson().toJson(it))
            }
          }
          "getSignImgAndSetItIfNotExist" -> {
            val stsSignImgSetting = methodCall.argument<String>("stsSignImgSetting")
            val fromJson: StsSignImgSetting = if (stsSignImgSetting.isNullOrEmpty()) StsSignImgSetting() else Gson().fromJson(stsSignImgSetting, StsSignImgSetting::class.java)

            STShield.getSignImgAndSetItIfNotExist(activity, fromJson) {
              channelResult.success(Gson().toJson(it))
            }
          }
          "getSignImgWithDrawingBoard" -> {
            val stsSignImgSetting = methodCall.argument<String>("stsSignImgSetting")
            val fromJson: StsSignImgSetting = if (stsSignImgSetting.isNullOrEmpty()) StsSignImgSetting() else Gson().fromJson(stsSignImgSetting, StsSignImgSetting::class.java)

            STShield.getSignImgWithDrawingBoard(activity, fromJson) {
              channelResult.success(Gson().toJson(it))
            }
          }
          "getFingerprintStatus" -> {
            channelResult.success(STShield.getFingerprintStatus(activity))
          }
          "openFingerprint" -> {
            val open = methodCall.argument<Boolean>("open")!!

            STShield.openFingerprint(activity, open) {
              val hashMap = HashMap<String, Any>()
              hashMap["resultCode"] = it.resultCode
              hashMap["resultMsg"] = it.resultMsg
              channelResult.success(hashMap)
            }
          }
            else -> {
                channelResult.notImplemented()
            }
        }
    }
}
