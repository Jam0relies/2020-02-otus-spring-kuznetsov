<template>
  <div>
    <add-author-form v-on:authorsChanged="updateAuthors"></add-author-form>
    <table class="authors-table">
      <thead>
      <tr>
        <th>Id</th>
        <th>Name</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="author in authors">
        <td>{{ author.id }}</td>
        <td>{{ author.name }}</td>
        <td>
          <button @click="removeAuthor(author)">Delete</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>
<script>
import axios from 'axios'
import AddAuthorForm from "./AddAuthorForm";

export default {
  name: 'authors',
  components: {AddAuthorForm},
  data: function () {
    return {
      authors: []
    }
  },
  methods: {
    removeAuthor(author) {
      axios.delete('/api/authors/' + author.id).then(() =>
        this.updateAuthors());
    },
    updateAuthors() {
      axios.get('/api/authors').then(response => response.data)
        .then(authors => {
          this.authors = authors;
        })
    },
  },
  mounted: function () {
    this.updateAuthors();
  },
};
</script>
