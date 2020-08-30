<template>
  <div>
    <form class="genre-form" v-on:submit.prevent="addGenre">
      <div>
        <label>Genre name
          <input type="text" v-model="newGenre.name" @submit.prevent/>
        </label>
      </div>
      <button>Add</button>
    </form>
  </div>
</template>
<script>
import axios from 'axios'

export default {
  data() {
    return {
      newGenre: {
        name: "",
      },
    }
  },
  methods: {
    addGenre(event) {
      event.preventDefault();
      console.log(event)
      axios.post('/api/genres', this.newGenre)
        .then(() => this.$emit('genresChanged',));
    },
  },
};
</script>
