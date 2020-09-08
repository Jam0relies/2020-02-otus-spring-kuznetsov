<template>
  <div>
    <ul>
      <li v-for="author in authors">
        <span>{{ author.id }} {{ author.name }}</span>
        <button @click="removeAuthor(author.id)">Delete</button>
      </li>
    </ul>
    <form class="book-author-form" v-on:submit.prevent="addAuthor">
      <select v-model="selectedAuthorId">
        <option v-for="author in authorsToAdd" v-bind:value="author.id"> {{ author.id }} {{ author.name }}</option>
      </select>
      <button>add</button>
    </form>
  </div>
</template>
<script>
import axios from "axios";

export default {
  props: ['bookId', 'authors'],
  data() {
    return {
      authorsToAdd: [],
      selectedAuthorId: "",
    };
  },
  methods: {
    removeAuthor(authorId) {
      axios.delete('/api/books/' + this.bookId + "/authors/" + authorId).then(() => {
        this.updateAuthors();
        this.updateAuthorsToAdd();
      });
    },
    updateAuthors() {
      axios.get('/api/books/' + this.bookId + '/authors').then(response => response.data)
        .then(authors => {
          this.authors = authors;
        })
    },
    updateAuthorsToAdd() {
      axios.get('/api/books/' + this.bookId + '/authors-to-add').then(response => response.data)
        .then(authors => {
          this.authorsToAdd = authors;
        })
    }
    ,
    addAuthor(event) {
      event.preventDefault();
      axios.post('/api/books/' + this.bookId + '/authors/' + this.selectedAuthorId,)
        .then(() => {
          this.updateAuthors();
          this.updateAuthorsToAdd();
        });
    }
  },
  mounted() {
    this.updateAuthorsToAdd();
  }
}
</script>
