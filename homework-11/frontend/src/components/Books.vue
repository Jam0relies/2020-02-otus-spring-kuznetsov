<template>
  <div>
    <add-book-form v-on:booksChanged="updateBooks"></add-book-form>
    <table class="books-table">
      <thead>
      <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Authors</th>
        <th>Genres</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="book in books">
        <td><a v-bind:href="'/books/' + book.id">{{ book.id }}</a></td>
        <td>{{ book.name }}</td>
        <td>
          <div v-for="author in book.authors">{{ author.name }}</div>
        </td>
        <td>
          <div v-for="genre in book.genres">{{ genre.name }}</div>
        </td>
        <td>
          <button @click="removeBook(book)">Delete</button>
        </td>
      </tr>
      </tbody>

    </table>
  </div>
</template>
<script>
import axios from 'axios'
import AddBookForm from "./AddBookForm";

export default {
  data() {
    return {
      books: [],
    }
  },
  components: {
    AddBookForm
  },
  methods: {
    removeBook(book) {
      axios.delete('/api/books/' + book.id).then(() =>
        this.updateBooks());
    },
    updateBooks() {
      axios.get('/api/books').then(response => response.data)
        .then(books => {
          this.books = books;
        })
    },
  },
  mounted: function () {
    this.updateBooks();
  },
};
</script>
