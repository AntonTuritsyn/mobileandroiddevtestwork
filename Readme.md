# Тестовое задание Android (NTPro)

![NTProgress_logo.png](NTProgress_logo.png)

## 📈 Легенда

---

Привет. Поздравляем, ты пришёл на работу в компанию, которая разрабатывает систему по торговле валютой. Но вот незадача — у них нет мобильного приложения (кошмар!). Система ещё молодая, поэтому функций у неё немного. И тебя попросили создать приложение под Android с основной функцией — просмотром сделок. 

## 🔮 **Что необходимо сделать**

---

Реализовать экран с таблицей сделок.

Реализуй сортировку таблицы по следующим полям: “дата изменения сделки“, “имя инструмента”, “цена сделки”, “объем сделки” и “сторона сделки”. При этом по умолчанию отсортируй таблицу по полю “дата изменения сделки“. Так же реализуй интерфейс изменения направления сортровки.

Приходящие сделки перед отображением сортируй согласно выбранным параметрам сортировки.

## 📦 **Что у тебя есть**

---

По [ссылке](https://bitbucket.org/ntprog/mobileandroiddevtestwork/src/master/) лежит шаблон пустого приложения. В нем есть файл Server, который нужно использовать для получения данных.


```kotlin
server.subscribeToDeals { 
    // код обработки
}
```

В callback этого метода приходит список дата классов Deal.

```kotlin
data class Deal(
        val id: Long,
        val timeStamp: Date,
        val instrumentName: String,
        val price: Double,
        val amount: Double,
        val side: Side,
    ) {
        enum class Side {
            SELL, BUY
        }
    }
```

После вызова метода `subscribeToDeals`, в callback асинхронно будут приходить пачки по 1000 сделок. Они будут приходить до тех пор, пока не закончатся. Общее количество сделок - около одного миллиона.

## 🔨 **Требования по реализации**

---

- Цена сделки и объем сделки (поля price и amount) приходят в Double, цену надо округлить до сотых, а объем до целых.
- На экране должен быть интерфейс для того, чтобы сменить поле сортировки и направление сортировки.
- В зависимости от стороны сделки необходимо подкрашивать цену либо в красный - для sell, либо в зеленый для buy.
- При скроле списка он не должен тормозить.
- Проект должен быть выполнен в git-репозитории, на который необходимо предоставить ссылку.
- Делать изменения в классе Server нельзя. Необходимо строить решение, подразумевая, что в любой момент времени может прилететь новая пачка со сделками.