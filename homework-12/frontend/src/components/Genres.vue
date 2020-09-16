<template>
  <div>
    <add-genre-form v-on:genresChanged="updateGenres"></add-genre-form>
    <table class="genres-table">
      <thead>
      <tr>
        <th>Id</th>
        <th>Name</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="genre in genres">
        <td>{{ genre.id }}</td>
        <td>{{ genre.name }}</td>
        <td>
          <button @click="removeGenre(genre)">Delete</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>
<script>
import axios from 'axios'
import AddGenreForm from "./AddGenreForm";

export default {
  data() {
    return {
      genres: [],
    }
  },
  components: {AddGenreForm},
  methods: {
    removeGenre(genre) {
      axios.delete('/api/genres/' + genre.id).then(() =>
        this.updateGenres());
    },
    updateGenres() {
      axios.get('/api/genres').then(response => response.data)
        .then(genres => {
          this.genres = genres;
        })
    },
  },
  mounted: function () {
    this.updateGenres();
  },
};
</script>
