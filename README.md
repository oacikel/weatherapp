Sorulacaklar:
    -LiveDataCallAdapterFactory (NetworkModule.kt'de var) ne yapıyor tam olarak?
    -LiveDataCallAdapter (LiveDataCallAdapterFactory'nin içinde) nedir? -> Galiba gelen response'u standart ApiResponse şekline sokuyor.
    -Signining konusunu anlamadım
    -Encryptor için bir yere üyelik ya da kendi şifremi yaratma gibi yapmam gereken bir şey var mıdır?
    -Karşı taraf encrypted data almıyor ise eğer benim encryptor'u kapatmam mı gerekir?
    -BaseInjectableActivity'de:
val langCode = sharedPref.getString("LangCode", defaultValue) -> koduna rastladım. Bunu DataStrore'a taşımak daha iyi mi olur?
    -aynı şekilde Loyalty'de shared preferences'a bu tag ile bir yükleme göremedim ama get metodu çağırılıyor. Ne iş?
    -dialog_success'in text'ini de  android:text="@{successMessage == null? @string/loading_popup_default_message : successMessage}" yapsak güzel olmaz mı?
Ya da buna hiç gerek yok mu?
    -CommonSuccessDialog'un viewModel'i CommonDialogViewModel ama LoadingDialogFragment'ınki SocialViewModel ortak tutmak daha şık olur mu?
    -Error/Loadig/Success'i BaseActivity'de tanımlasak daha iyi olur mu?
    -Resource nedir?
    -İnternetsiz sadece lokalde data tutmanın en iyi yolu nedir?
    -pagination nasıl olacak?
    -coordinator layout ne işe yarıyor allasen
    -keza timber???
    -callbackleri, adapterları inject edebilir miyiz? Evet ise nasıl? Etmek mantıklı mı?

Sorunlar:
-BaseAuth'un bir şekilde yazılması lazım :( -> Yazdım ama SessionEndDialogFragment isteyen endSession function'ununu iptal ettim.
-Navigation'u halletmem lazım

//
Pinner backend ile data alışverişindee request ile giden sertifika


//
BASE_URL
MARVEL_API_PUBLIC_KEY
MARVEL_API_PRIVATE_KEY değerleri bu uygulama için global properties dosyasını benim bilgisayarımdan alarak set ediliyor. Başka projelerde bu bilgiler güncellenmeli