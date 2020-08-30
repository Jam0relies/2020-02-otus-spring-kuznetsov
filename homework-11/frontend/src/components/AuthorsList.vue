<template>
  <div>
    <ul>
      <li v-for="author in authors">
        <span>{{ author.name }}</span>
        <form action="#"
              th:action="@{/books/{bookId}/authors/{authorId}/remove(bookId=${book.id}, authorId=${author.id})}"
              method="post">
          <button type="submit">delete</button>
        </form>
      </li>
    </ul>
    <form action="#" th:action="@{/books/{bookId}/authors (bookId=${book.id})}" th:object="${authorToAdd}"
          method="post">
      <select th:field="*{id}">
        <option th:each="author : ${authors}" th:value="${author.id}" th:text="${author.name}"></option>
      </select>
      <button type="submit">add</button>
    </form>
  </div>
</template>
<script>
export default {
  props: ['bookId', 'authors'],
}
</script>
