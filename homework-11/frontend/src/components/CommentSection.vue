<template>
  <div>
    <form v-on:submit.prevent="addComment">
      <textarea v-model="newComment.text"></textarea>
      <button>Add</button>
    </form>
    <div>
      <div v-for="comment in comments">
        <div>{{ comment.timestamp }}</div>
        <div>{{ comment.text }}</div>
      </div>
    </div>
  </div>
  <!--      <form action="#" th:action="@{/books/{bookId}/comments (bookId=${book.id})}" th:object="${commentToAdd}"-->
  <!--            method="post">-->
  <!--        <textarea th:field="*{text}"></textarea>-->
  <!--        <button type="submit">add</button>-->
  <!--      </form>-->
  <!--      <div>-->
  <!--        <div th:each="comment : ${book.comments}">-->
  <!--          <div th:text="${comment.timestamp}"></div>-->
  <!--          <div th:text="${comment.text}"></div>-->
  <!--        </div>-->
  <!--      </div>-->
</template>
<script>
import axios from "axios";

export default {
  props: ['bookId'],
  data() {
    return {
      comments: [],
      newComment: {text: ""}
    }
  },
  methods: {
    updateComments() {
      axios.get('/api/books/' + this.bookId + '/comments').then(response => response.data)
        .then(comments => {
          this.comments = comments;
        })
    },
    addComment(event) {
      event.preventDefault();
      axios.post('/api/books/' + this.bookId + '/comments/', this.newComment)
        .then(() => {
          this.updateComments();
        });
    }
  },
  mounted() {
    this.updateComments();
  }
}
</script>
