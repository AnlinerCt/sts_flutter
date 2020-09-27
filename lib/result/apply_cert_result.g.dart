// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'apply_cert_result.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ApplyCertResult _$ApplyCertResultFromJson(Map<String, dynamic> json) {
  return ApplyCertResult(
    resultCode: json['resultCode'] as int,
    resultMsg: json['resultMsg'] as String,
    enCert: json['enCert'] as String,
    signCert: json['signCert'] as String,
    stsCertInfo: json['stsCertInfo'] == null
        ? null
        : StsCertInfo.fromJson(Map<String, dynamic>.from(json['stsCertInfo'])),
    stsUserInfo: json['stsUserInfo'] == null
        ? null
        : StsUserInfo.fromJson(Map<String, dynamic>.from(json['stsUserInfo'])),
    stsCompanyInfo: json['stsCompanyInfo'] == null
        ? null
        : StsCompanyInfo.fromJson(Map<String, dynamic>.from(json['stsCompanyInfo'])),
  );
}

Map<String, dynamic> _$ApplyCertResultToJson(ApplyCertResult instance) =>
    <String, dynamic>{
      'resultCode': instance.resultCode,
      'resultMsg': instance.resultMsg,
      'enCert': instance.enCert,
      'signCert': instance.signCert,
      'stsCertInfo': instance.stsCertInfo,
      'stsUserInfo': instance.stsUserInfo,
      'stsCompanyInfo': instance.stsCompanyInfo,
    };
