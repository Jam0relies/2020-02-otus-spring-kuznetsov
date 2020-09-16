<template>
  <div>
    <form class="author-form" v-on:submit.prevent="addAuthor">
      <div>
        <label>Author name
          <input type="text" v-model="newAuthor.name" @submit.prevent/>
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
