# smddevTestTask

### Запуск
Чтобы запустить сервис необходимо выполнить следующие шаги:
* склонировать репозиторий ```git clone https://github.com/StasyaGera/smddevTestTask.git```
* перейти в появившийся каталог ```cd smddevTestTask```
* выполнить команду ```docker-compose up -d```

После выполнения этих шагов сервис будет доступен по адресу ```http://localhost:8080/products```.

### Тестирование
Тестовый сценарий:
  * создать товар 
  * найти его по параметру
  * получить детали найденного товара

Команды ```curl```:
```
curl -H "Content-Type: application/json" -X POST -d '{"id":"5d63e5660815ee34c046f6ff", "name":"iPhone 8","description":"Apple iPhone 258GB.","parameters":[{"key":"memory", "value":"256GB"}, {"key":"camera", "value":"14MP"}]}' http://localhost:8080/products
```
```
curl http://localhost:8080/products/search?camera=14MP
```
```
curl http://localhost:8080/products/5d63e5660815ee34c046f6ff
```
