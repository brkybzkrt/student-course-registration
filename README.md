# Öğrenci Ders Kayıt Sistemi / Student Course Registration System

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

---

## 🇬🇧 English

### About the Project
This project is an application with a microservice architecture that enables students to register for courses, manage their course selections, and track their course registrations. The system supports core functions such as student and course management, registration processes, and notification delivery.

### Technologies and Tools
- **Java 21**: Core programming language of the application
- **Spring Boot 3.5.0**: Application framework
- **Spring Cloud**: Tools for microservice architecture
- **Spring Data JPA**: ORM solution for database operations
- **Spring Security**: Security and authentication
- **Keycloak**: Identity and access management
- **PostgreSQL**: Relational database
- **Kafka**: Asynchronous messaging and event-based communication
- **Feign Client**: HTTP communication between services
- **Lombok**: Helper library to reduce code repetition
- **Springdoc OpenAPI**: API documentation

### Architectural Structure
The project includes the following microservices and has a parent module structure.
Microservices can easily use the necessary common packages by adding them to their dependencies:

#### Services
1. **student-service**: Management of student information
2. **course-service**: Management of course information
3. **registration-service**: Management of course registration/approval processes
4. **admin-service**: Management of administrative operations
5. **notification-service**: Management of notification operations (Email)

#### Common Modules
1. **auth-common**: Keycloak-based authentication and authorization
2. **base-interface-common**: Basic interface/dto/response/swagger definitions
3. **email-common**: Common functions for email sending
4. **entity-common**: Database entity definitions
5. **exception-filter-common**: Error management
6. **kafka-common**: Kafka messaging configuration (topics, dtos)
7. **postgres-common**: A common BaseEntity configuration that all PostgreSQL entities will inherit

### Data Model
Core entities of the system:
- **Student**: Student information
- **Course**: Course information
- **CourseRegistration**: Course registration information
- **Admin**: Administrator information

### Security
- **OpenID Connect**: Authentication with Keycloak
- **Role-Based Access Control**: Role-based access control
- **JWT Token**: Token-based system for secure authentication
