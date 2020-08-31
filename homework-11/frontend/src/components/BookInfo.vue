<template>
  <div>
    <h1>{{ book.name }}</h1>
    <div>
      <h2>Authors</h2>
      <authors-list v-bind:book-id="id" v-bind:authors="book.authors"></authors-list>
      <h2>Genres</h2>
      <genres-list v-bind:book-id="id" v-bind:genres="book.genres"></genres-list>
      <h2>Comments</h2>
      <comment-section v-bind:book-id="id"></comment-section>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import AuthorsList from "./AuthorsList";
import GenresList from "./GenresList";
import CommentSection from "./CommentSection";

export default {
  props: ['id'],
  data() {
    return {
      book: null
    }
  },
  components: {AuthorsList, GenresList, CommentSection},
  methods: {
    updateBook() {
      axios.get('/api/books/' + this.id).then(response => response.data)
        .then(book => {
          this.book = book;
        })
    },
  },
  mounted: function () {
    this.updateBook();
  },
}
</script>
