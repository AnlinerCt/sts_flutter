# sts_flutter

AHCA STS Flutter plugin.
AHCA Flutter平台手机盾SDK插件

# 说明

此插件是在手机盾原生SDK上封装的Flutter平台插件，其功能和接口与原生SDK一致。
目前只支持Android平台的原生功能，iOS部分的待定。

## 初始化
初始化手机盾配置，建议在自定义Application类的onCreate生命方法内操作：
StsFlutter.init("服务地址", "AppKey", "SecretKey");
StsFlutter.initFace("活体检测授权码", "活体检测授权文件名");
注意，同时需要在确定用户信息和唯一标识后调用以下接口：
StsFlutter.initUseId("用户唯一标识");

## 可选的流程
请根据实际需求进行配置，例如项目无需使用手机盾活体检测功能，则无需配置initFace。
在使用证书前（例如签名、签章等），需要先判断证书是否已申请，如果证书不存在，需要调用申请证书接口，申请成功后方可使用。建议使用以下几种实现方案：
A.直接使用证书功能，例如调用证书签名/证书签章/证书登录等接口，如果得到返回码为10502，则再调用申请证书接口，申请成功后再次调用证书签名等对应接口进行操作。
B.使用证书签名/签章/登录等接口前，先调用checkCert接口，检查证书状态，如果得到返回码为10502，则调用申请证书接口，申请成功后再次调用证书签名等对应接口进行操作，得到返回码为10503，则可以直接使用证书功能。
C.调用isLocalCertExist接口，判断本地证书是否存在。
**实际上A方案提到的所有涉及证书数据的接口，均包含了checkCert接口的功能，单独提供checkCert以及isLocalCertExist接口，为了方便多样化业务场景，例如，APP证书登录场景，由于checkCert、certLogin或其他接口需要联网，存在一定的耗时，可仅使用isLocalCertExist接口仅判断本地证书是否存在，证书存在则调用证书登录接口，证书不存在则显示密码输入框或验证码相关控件，进行后续申请证书的操作。

## StsCodeTable
StsCodeTable类包含了SDK接口返回的部分错误码和对应解释，例如常用的 1(成功) 10502(参数不全或为空) 10502(证书不存在或已损坏) 10503(证书已存在)等

## StsFlutter
StsFlutter类包含接口外，还包括部分接口使用的固定标识
如申请个人证书接口的StsUserInfo实体类的证件类型属性(cardType)即可使用以下标识
  static const String CARD_TYPE_ID_CARD = "00"; // 身份证
  static const String CARD_TYPE_TEMPORARY_ID_CARD = "01"; // 临时身份证
  static const String CARD_TYPE_HOUSEHOLD_REGISTER = "02"; // 户口本
  static const String CARD_TYPE_PASSPORT = "03"; // 护照
  static const String CARD_TYPE_MILITARY_ID_CARD = "04"; // 军人身份证
  static const String CARD_TYPE_ARMED_POLICE_ID_CARD = "05"; // 武警身份证
  
如certSign、certLogin等接口的dataType、dataFormat参数可使用如下标识
  static const String DATA_TYPE_ORIGINAL_TO_HEXADECIMAL = "0"; // 原文数据转成16进制
  static const String DATA_TYPE_BYTE_BY_HEXADECIMAL = "1"; // byte类型数据（16进制编码）
  static const String DATA_TYPE_CHINESE_TO_HEXADECIMAL = "2"; // 中文转成16进制
  static const String DATA_TYPE_ORIGINAL = "3"; // 原文数据
  static const String DATA_TYPE_BYTE_BY_BASE64 = "4"; // byte类型数据（Base64编码加密后的值）

  static const String DATA_FORMAT_P1 = "p1"; // P1
  static const String DATA_FORMAT_P7 = "p7"; // P7
  static const String DATA_FORMAT_P7_ENVELOPE = "p7envelope"; // P7数字信封
  static const String DATA_FORMAT_DEFAULT = "default"; // 裸签名