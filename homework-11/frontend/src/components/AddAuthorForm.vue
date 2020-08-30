<template>
  <div>
    <form id="author-form" v-on:submit.prevent="addAuthor">
      <div>
        <label for="newAuthorName">Author name</label>
        <input id="newAuthorName" type="text" v-model="newAuthor.name" @submit.prevent/>
      </div>
      <button>Add</button>
    </form>
  </div>
</template>
<script>
import axios from 'axios'

export default {
  el: '#author-form',
  data() {
    return {
      newAuthor: {
        name: "",
      },
    }
  },
  methods: {
    addAuthor(event) {
      event.preventDefault();
      console.log(event)
      axios.post('/api/authors', this.newAuthor)
        .then(() => this.$emit('authorsChanged',));
    },
  },
};
</script>
