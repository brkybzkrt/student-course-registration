# Öğrenci Ders Kayıt Sistemi (Student Course Registration System)

## 🇹🇷 Türkçe

### Proje Hakkında
Bu proje, öğrencilerin derslere kayıt olmasını, ders seçimlerini yönetmesini ve ders kayıtlarını takip etmesini sağlayan bir mikroservis mimarisine sahip uygulamadır. Sistem, öğrenci ve ders yönetimi, kayıt işlemleri ve bildirim gönderimi gibi temel işlevleri desteklemektedir.

### Teknolojiler ve Araçlar
- **Java 21**: Uygulamanın temel programlama dili
- **Spring Boot 3.5.0**: Uygulama çerçevesi
- **Spring Cloud**: Mikroservis mimarisi için araçlar
- **Spring Data JPA**: Veritabanı işlemleri için ORM çözümü
- **Spring Security**: Güvenlik ve kimlik doğrulama
- **Keycloak**: Kimlik ve erişim yönetimi
- **PostgreSQL**: İlişkisel veritabanı
- **Kafka**: Asenkron mesajlaşma ve olay tabanlı iletişim
- **Feign Client**: Servisler arası HTTP iletişimi
- **Lombok**: Kod tekrarını azaltmak için yardımcı kütüphane
- **Springdoc OpenAPI**: API dokümantasyonu

### Mimari Yapı
Proje, aşağıdaki mikroservisleri içermekte olup aynı zamanda parent module yapısına sahiptir. 
Microserviceler gerekli common paketleri kendi bağımlılıklarına kolayca ekleyerek kullanabilir:


#### Servisler
1. **student-service**: Öğrenci bilgilerinin yönetimi
2. **course-service**: Ders bilgilerinin yönetimi
3. **registration-service**: Ders kayıt/onay işlemlerinin yönetimi
4. **admin-service**: Yönetici işlemlerinin yönetimi
5. **notification-service**: Bildirim işlemlerinin yönetimi (Email)

#### Ortak Modüller
1. **auth-common**: Keycloak tabanlı kimlik doğrulama ve yetkilendirme
2. **base-interface-common**: Temel interface/dto/response/swagger tanımlamaları
3. **email-common**: E-posta gönderimi için ortak fonksiyonlar
4. **entity-common**: Veritabanı entity tanımlamaları
5. **exception-filter-common**: Hata yönetimi
6. **kafka-common**: Kafka mesajlaşma yapılandırması (topics, dtos)
7. **postgres-common**: Tüm PostgreSQL entitylerinin miras alacağı ortak bir BaseEntity yapılandırması


### Veri Modeli
Sistemin temel varlıkları:
- **Student**: Öğrenci bilgileri
- **Course**: Ders bilgileri
- **CourseRegistration**: Ders kayıt bilgileri
- **Admin**: Yönetici bilgileri

### Güvenlik
- **OpenID Connect**: Keycloak ile kimlik doğrulama
- **Role-Based Access Control**: Rol tabanlı erişim kontrolü
- **JWT Token**: Güvenli kimlik doğrulama için token tabanlı sistem
