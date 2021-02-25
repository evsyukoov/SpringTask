# SpringTask

## Установка

###  Создание образа
git clone https://github.com/evsyukoov/SpringTask.git

cd SpringTask

docker build . -t service

### Запуск сервиса

1) docker run -it -p 80:8080 service - запуск сервиса

2) docker run -it -p 80:8080 -e TEST=1 service - перед запуском соберутся и запустятся тесты, 

результаты тестов будут выведены в консоль

###  Документация к API сервиса

1) GET / - доступ к документации

2) GET /compare/currency_code - основной функционал

Список кодов валюты можно посмотреть на главной странице сервиса

    Примечание:
    
      Валюта по отношению к которой смотрится курс по умолчанию RUB,
      можно поменять в файле application.properties

    Примеры корректных запросов:
    
      http://localhost/compare/eur
      http://localhost/compare/usd
      http://localhost/compare/AFN
      
###  Коды возврата

1) 400 Bad Request - запрос к сервису c неправильным кодом валюты

2) 404 Not found - любой запрос не указанный в документации

2) 500 Internal Server Error - любые ошибки на внешних сервисах, кроме 429

3) 429 Too Many Requests - слишком много запросов к внешнему сервису, наш сервис будет возврашать такую же ошибку

4) 302 Redirect - все ок, перенаправили клиента по ссылке с картинкой


