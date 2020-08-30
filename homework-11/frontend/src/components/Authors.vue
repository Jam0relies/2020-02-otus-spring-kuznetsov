<template>
  <div>
    <!--  <form id="author-form" v-on:submit.prevent="addAuthor">-->
    <!--    <div>-->
    <!--      <label for="newAuthorName">Author name</label>-->
    <!--      <input id="newAuthorName" type="text" v-model="newAuthor.name" @submit.prevent/>-->
    <!--    </div>-->
    <!--    <button>Add</button>-->
    <!--  </form>-->
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

export default {
  name: 'authors',
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
  created: function () {

    // bus.$on('authorsChanged', () => {
    //   this.updateAuthors();
    // })
  }
};
// new Vue({
//   el: '#author-form',
//   data: {
//     newAuthor: {
//       name: "",
//     },
//   },
//   methods: {
//     addAuthor(event) {
//       event.preventDefault();
//       console.log(event)
//       this.$http.post('/api/authors', this.newAuthor)
//         .then(() => bus.$emit('authorsChanged',));
//     },
//   },
// });
</script>
