<template>
  <div>
    <ul>
      <li v-for="genre in genres">
        <span>{{ genre.id }} {{ genre.name }}</span>
        <button @click="removeGenre(genre.id)">Delete</button>
      </li>
    </ul>
    <form class="book-genre-form" v-on:submit.prevent="addGenre">
      <select v-model="selectedGenreId">
        <option v-for="genre in genresToAdd" v-bind:value="genre.id"> {{ genre.id }} {{ genre.name }}</option>
      </select>
      <button>add</button>
    </form>
  </div>
</template>
<script>
import axios from "axios";

export default {
  props: ['bookId', 'genres'],
  data() {
    return {
      genresToAdd: [],
      selectedGenreId: "",
    };
  },
  methods: {
    removeGenre(genreId) {
      axios.delete('/api/books/' + this.bookId + "/genres/" + genreId).then(() => {
        this.updateGenres();
        this.updateGenresToAdd();
      });
    },
    updateGenres() {
      axios.get('/api/books/' + this.bookId + '/genres').then(response => response.data)
        .then(genres => {
          this.genres = genres;
        })
    },
    updateGenresToAdd() {
      axios.get('/api/books/' + this.bookId + '/genres-to-add').then(response => response.data)
        .then(genres => {
          this.genresToAdd = genres;
        })
    }
    ,
    addGenre(event) {
      event.preventDefault();
      axios.post('/api/books/' + this.bookId + '/genres/' + this.selectedGenreId,)
        .then(() => {
          this.updateGenres();
          this.updateGenresToAdd();
        });
    }
  },
  mounted() {
    this.updateGenresToAdd();
  }
}
</script>
