<template>
  <div>
    <table class="users-table">
      <thead>
      <tr>
        <th>Username</th>
        <th>Authorities</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="user in users">
        <td>{{ user.username }}</td>
        <td>
          <div v-for="authority in user.authorities">{{ authority }}</div>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>
<script>
import axios from 'axios'

export default {
  data() {
    return {
      users: [],
    }
  },
  methods: {
    updateUsers() {
      axios.get('/api/users').then(response => response.data)
        .then(users => {
          this.users = users;
        })
    },
  },
  mounted: function () {
    this.updateUsers();
  },
};
</script>
