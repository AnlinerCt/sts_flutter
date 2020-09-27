import 'dart:async';

import 'package:flutter/services.dart';

class StsPlatformBridge {
  static const MethodChannel _methodChannel = const MethodChannel('sts_flutter');

//  证书相关
  static Future<String> init(
    String url,
    String appKey,
    String secretKey,
  ) async {
    if (url == null || url.isEmpty || appKey == null || appKey.isEmpty || secretKey == null || secretKey.isEmpty) {
      return "初始化手机盾失败，参数为空";
    } else {
      try {
        return await _methodChannel.invokeMethod("init", {
          "baseUrl": url,
          "stsAppKey": appKey,
          "stsSecretKey": secretKey,
        });
      } on Exception catch (e) {
        return e.toString();
      }
    }
  }

  static Future<String> initUseId(
    String useId,
  ) async {
    if (useId == null || useId.isEmpty) {
      return "初始化手机盾用户唯一标识失败，参数为空";
    } else {
      try {
        return await _methodChannel.invokeMethod("initUseId", {
          "useId": useId,
        });
      } on Exception catch (e) {
        return e.toString();
      }
    }
  }

  static Future<String> initFace(
    String id,
    String fileName,
  ) async {
    if (id == null || id.isEmpty || fileName == null || fileName.isEmpty) {
      return "初始化手机盾人脸识别模块失败，参数为空";
    } else {
      try {
        return await _methodChannel.invokeMethod("initFace", {
          "id": id,
          "fileName": fileName,
        });
      } on Exception catch (e) {
        return e.toString();
      }
    }
  }

  static Future<String> initThemeColor(
    int color,
  ) async {
    if (color <= 0) {
      return "初始化手机盾主题色失败，色值小于等于零";
    } else {
      try {
        return await _methodChannel.invokeMethod("initThemeColor", {
          "color": color,
        });
      } on Exception catch (e) {
        return e.toString();
      }
    }
  }

  static Future<String> get getDeviceID async {
    return await _methodChannel.invokeMethod("getDeviceID");
  }

  static Future<bool> get isLocalCertExist async {
    return await _methodChannel.invokeMethod("isLocalCertExist");
  }

  static Future<Map<String, dynamic>> get checkCert async {
    return await _methodChannel.invokeMapMethod("checkCert");
  }

  static Future<Map<String, dynamic>> get clearCert async {
    return await _methodChannel.invokeMapMethod("clearCert");
  }

  static Future<String> applyPersonalCert(
    String stsUserInfo,
  ) async {
    return await _methodChannel.invokeMethod("applyPersonalCert", {
      "stsUserInfo": stsUserInfo,
    });
  }

  static Future<String> applyCompanyCert(
      String stsCompanyInfo,
  ) async {
    return await _methodChannel.invokeMethod("applyCompanyCert", {
      "stsCompanyInfo": stsCompanyInfo,
    });
  }

  static Future<Map<String, dynamic>> get getUntieEquipmentQRcode async {
    return await _methodChannel.invokeMapMethod("getUntieEquipmentQRcode");
  }

  static Future<String> untieEquipment(
    String qrData,
  ) async {
    return await _methodChannel.invokeMethod("untieEquipment", {
      "qrData",
      qrData,
    });
  }

  static Future<Map<String, dynamic>> updatePersonalCert(
      String stsUserInfo,
  ) async {
    return await _methodChannel.invokeMapMethod("updatePersonalCert", {
      "stsUserInfo": stsUserInfo,
    });
  }

  static Future<Map<String, dynamic>> updateCompanyCert(
      String stsCompanyInfo,
  ) async {
    return await _methodChannel.invokeMapMethod("updateCompanyCert", {
      "stsCompanyInfo": stsCompanyInfo,
    });
  }

  static Future<Map<String, dynamic>> resetPersonalPIN(
      String stsUserInfo,
  ) async {
    return await _methodChannel.invokeMapMethod("resetPersonalPIN", {
      "stsUserInfo": stsUserInfo,
    });
  }

  static Future<Map<String, dynamic>> resetCompanyPIN(
      String stsCompanyInfo,
  ) async {
    return await _methodChannel.invokeMapMethod("resetCompanyPIN", {
      "stsCompanyInfo": stsCompanyInfo,
    });
  }

  static Future<Map<String, dynamic>> get modifyPIN async {
    return await _methodChannel.invokeMapMethod("modifyPIN");
  }

  static Future<Map<String, dynamic>> scanLogin(
      String stsScanInfo,
  ) async {
    return await _methodChannel.invokeMapMethod("scanLogin", {
      "stsScanInfo": stsScanInfo,
    });
  }

  static Future<Map<String, dynamic>> scanSign(
      String stsScanInfo,
  ) async {
    return await _methodChannel.invokeMapMethod("scanSign", {
      "stsScanInfo": stsScanInfo,
    });
  }

  static Future<Map<String, dynamic>> certLogin(
    String data,
    String dataFormat,
    String dataType,
    String pn,
  ) async {
    return await _methodChannel.invokeMapMethod("certLogin", {
      "data": data,
      "dataFormat": dataFormat,
      "dataType": dataType,
      "pn": pn,
    });
  }

  static Future<Map<String, dynamic>> certSeal(
    String pn,
  ) async {
    return await _methodChannel.invokeMapMethod("certSeal", {
      "pn": pn,
    });
  }

  static Future<Map<String, dynamic>> certSign(
    String data,
    String dataFormat,
    String dataType,
    String pn,
  ) async {
    return await _methodChannel.invokeMapMethod("certSign", {
      "data": data,
      "dataFormat": dataFormat,
      "dataType": dataType,
      "pn": pn,
    });
  }

  static Future<Map<String, dynamic>> certVerifySign(
    String data,
    String dataFormat,
    String signData,
    String dataType,
  ) async {
    return await _methodChannel.invokeMapMethod("certVerifySign", {
      "data": data,
      "dataFormat": dataFormat,
      "signData": signData,
      "dataType": dataType,
    });
  }

  static Future<Map<String, dynamic>> certEncrypt(
    String data,
    String dataFormat,
    String dataType,
  ) async {
    return await _methodChannel.invokeMapMethod("certEncrypt", {
      "data": data,
      "dataFormat": dataFormat,
      "dataType": dataType,
    });
  }

  static Future<Map<String, dynamic>> certDecrypt(
    String data,
    String dataFormat,
    String dataType,
    String pn,
  ) async {
    return await _methodChannel.invokeMapMethod("certDecrypt", {
      "data": data,
      "dataFormat": dataFormat,
      "dataType": dataType,
      "pn": pn,
    });
  }

  static Future<Map<String, dynamic>> changeCertStatus(
      int statusType,
  ) async {
    return await _methodChannel.invokeMapMethod("changeCertStatus", {
      "statusType": statusType,
    });
  }

  static Future<Map<String, dynamic>> get postponeCert async {
    return await _methodChannel.invokeMapMethod("postponeCert");
  }

  static Future<Map<String, dynamic>> setPKCacheTime(
    String pn,
  ) async {
    return await _methodChannel.invokeMapMethod("setPKCacheTime", {
      "pn": pn,
    });
  }

  static Future<Map<String, dynamic>> clearPKCacheTime(
    String pn,
  ) async {
    return await _methodChannel.invokeMapMethod("clearPKCacheTime", {
      "pn": pn,
    });
  }

  static Future<String> getCert(
    int certType,
  ) async {
    return await _methodChannel.invokeMethod("getCert", {
      "certType": certType,
    });
  }

  static Future<String> getLocalCert(
    int certType,
  ) async {
    return await _methodChannel.invokeMethod("getLocalCert", {
      "certType": certType,
    });
  }

  static Future<String> downloadCert(
    String phoneNum,
    String departmentNo,
    int certType,
  ) async {
    return await _methodChannel.invokeMethod("downloadCert", {
      "phoneNum": phoneNum,
      "departmentNo": departmentNo,
      "certType": certType,
    });
  }

  static Future<String> get getDepartmentNo async {
    return await _methodChannel.invokeMethod("getDepartmentNo");
  }

  static Future<String> setSignImgWithDrawingBoard(
      String stsSignImgSetting,
  ) async {
    return await _methodChannel.invokeMethod("setSignImgWithDrawingBoard", {
      "stsSignImgSetting": stsSignImgSetting,
    });
  }

  static Future<String> setSignImgBase64Str(
    String imgBase64,
  ) async {
    return await _methodChannel.invokeMethod("setSignImgBase64Str", {
      "imgBase64": imgBase64,
    });
  }

  static Future<String> get getSignImgFromService async {
    return await _methodChannel.invokeMethod("getSignImgFromService");
  }

  static Future<String> getSignImgAndSetItIfNotExist(
      String stsSignImgSetting,
  ) async {
    return await _methodChannel.invokeMethod("getSignImgAndSetItIfNotExist", {
      "stsSignImgSetting": stsSignImgSetting,
    });
  }

  static Future<String> getSignImgWithDrawingBoard(
      String stsSignImgSetting,
  ) async {
    return await _methodChannel.invokeMethod("getSignImgWithDrawingBoard", {
      "stsSignImgSetting": stsSignImgSetting,
    });
  }

  static Future<bool> get getFingerprintStatus {
    return _methodChannel.invokeMethod("getFingerprintStatus");
  }

  static Future<Map<String, dynamic>> openFingerprint(bool open) async {
    return await _methodChannel.invokeMapMethod("openFingerprint", {"open": open});
  }
}
