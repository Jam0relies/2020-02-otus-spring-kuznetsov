<template>
  <div>
    <form class="book-form" v-on:submit.prevent="addBook">
      <div>
        <label>Book name
          <input type="text" v-model="newBook.name" @submit.prevent/>
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
      newBook: {
        name: "",
      },
    }
  },
  methods: {
    addBook(event) {
      event.preventDefault();
      console.log(event)
      axios.post('/api/books', this.newBook)
        .then(() => this.$emit('booksChanged',));
    },
  },
};
</script>
