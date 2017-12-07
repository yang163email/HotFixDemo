package com.yan.hotfix

import android.app.Application
import com.taobao.sophix.PatchStatus
import com.taobao.sophix.SophixManager

/**
 *  @author      : 楠GG
 *  @date        : 2017/12/7 12:43
 *  @description : TODO
 */
class HotfixApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initSophix()
    }

    private fun initSophix() {
        val appVersion = this.packageManager.getPackageInfo(this.packageName, 0).versionName
        // initialize最好放在attachBaseContext最前面，初始化直接在Application类里面，切勿封装到其他类
        SophixManager.getInstance()
                .setContext(this)   //必选
                .setAppVersion(appVersion)    //必选，应用的版本号
                .setSecretMetaData( //可选，推荐使用。配置信息（没有配置的话，需要在清单文件中配置）
                        "24724076-1",
                        "87383a22311ad4e33ad3504963217cd0",
                        "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDOm4GR1AXT8wzW+x/DUr2ndA273Cx6LnXeE4VF41GyVtfNla+6Z16K6CympoeR38FPdrUGm7+JRqfz+U2y39Io2fXJpGQ6T2TBGoUOUJSKu+s+nP2fEfIb2hsGVgCRmsz5J3ZClUhi08Y/iraQTfTiQJJg2y7LXPi5cmzLRjuRjdI20rC1oZ1ZPHH80UT7/gty4XqDsBTi5Mt0bYNTl3P/OERV8HEvLVg7jEbx4OZaqLSrQXqEtwwcv5jwiGdZt/3hzQJ/J4RlWxv0PlFd+flsJbyCcukIpQrbkH/S0fwVqG6OTH8dKqtUn5eTK+aQcOVlrUPO6RIBbZjwiApT9jSDAgMBAAECggEBALma88SI349vqlo7EWbMHvqfBHN+sSo2MMcHbFPDijIXWBklXs+ra0CIoQw5UFMY2zXC44+DPYdaRSmlDksiTU9Lt0WlfDDyAAaGB4A7naYgeY4vRJyxZ3nj7yE7A5nKolc2P0RDjdrSeye8OpPapKLY9WczoI8q/3rBs8DNYFg5nD/JyZ2/DBjS0U8eEn/YOKl/tEZanbij2ehy5Ct/OhbfFiwWRLrhU3rFZaMHha+jHcvIV+mZt6oDuk/itu9+JUL9wrhfNBM86VrlQaRBoXPGPiYalZq1zKfOSy+Lcm/LbFeziavuM1FpanhTbNX87QB3s2Zgc5MDHW+ZOmcSi4ECgYEA+cK2D4UCbuTC1DUbQ5E8MxFEnN7UDV6PDqI7XzH3aVjqg5JfTnGxUplYyeqNHoqI2XuYoSVM4wAsq05gdX9NqBMR2ZVHMxDiUZWA/UbTtod83SqJt9H5b1TbfhuCkToaRZ8QtBiYPq3tEvrrUKAPyV7Gq66yNHY5kIO8e2/JPMMCgYEA08TRixKbkH+nltc3B/zNH0wXib7MBNvsJv53OPqwj+vfw+O4TPpXcGcyV9G7EH8pcklRUj58x0YnbkWpPlLxUTlU04gmoEYdOT/5MkooLhyFsiR0nTIC/L2So3uQMmiHXPJzpfk6Y8hT0s6Nkd4FtMOpW/NpQcEtwsf7jJgarUECgYB4Z3+xV6Pmpe2us7NjDV8CF28GWP3NPCqnGVqZ99KYPL9BkozTmker1DVTkflaIdr7TXQ1K3A3dtwR8YTa3/Yl8puGU0qxuZABWJuIfl6N/h8Z1+nwbg9btoLh9o0pSeNkcNxylfC/NsW1grVydtdlcoVAOsW6OMxjeL6imy9eVQKBgDrZ9rXkjPECWOu1kaiZm9O3zM7OBFrRV3aavz1JhenpTj1dOqnB5KzrMPVw5sC15inJsFzf2sNyfqDJoTgdysyc1zMWIk3mTlbLrZBJRZNS/+C6+uF4tZXRhUADpdYscHnnMzaL5as3T/xExNklz4Yl3hocskLA0dvQwdqXpspBAoGBAKY2RA8Dly3iS3LlSWax/UTzT4YTgVixNC4336iFEQxKHOvi1CqrkzF/QSGlu2L70U2yL5lMh1vl3PckM7wh16zL1apmpcyNRDIelhgRrCzy67P5k631F/UnMp9Ru9bbSZrbJUD6GutmeKjIk22/XSb2GmXuBKb3ovdQ/XKiZuyP"
                )
                .setAesKey(null)    //可选，用户自定义的AES秘钥，会对补丁包进行对称加密
                .setEnableDebug(true)   //可选，是否调试模式
                //.setUnsupportedModel(modelName, sdkVersion) //可选，把不支持的设备加入黑名单，加入后不会进行热修复
                .setPatchLoadStatusStub {   //可选, 设置patch加载状态监听器
                    mode,   // 补丁模式, 0:正常请求模式 1:扫码模式 2:本地补丁模式
                    code,   //补丁加载状态码, 详情查看PatchStatusCode类说明
                    info,   //补丁加载详细说明, 详情查看PatchStatusCode类说明
                    handlePatchVersion  //当前处理的补丁版本号, 0:无 -1:本地补丁 其它:后台补丁
                    ->
                    // 补丁加载回调通知
                    if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                        // 表明补丁加载成功
                    } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                        // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                        // 建议: 用户可以监听进入后台事件, 然后调用killProcessSafely自杀，以此加快应用补丁，详见1.3.2.3
                    } else {
                        // 其它错误信息, 查看PatchStatus类说明
                    }
                }.initialize()
        // queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，建议放在后面任意时刻，如onCreate中
        SophixManager.getInstance().queryAndLoadNewPatch()
    }
}