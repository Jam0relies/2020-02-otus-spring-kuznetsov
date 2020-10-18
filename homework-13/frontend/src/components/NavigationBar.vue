<template>
  <header id="header">
    <nav>
      <div v-if="hasUserAuthority">
        <router-link
          active-class="is-active"
          class="link"
          :to="{ name: 'books', params: { } }">
          Books
        </router-link>
      </div>
      <div v-if="hasUserAuthority">
        <router-link
          active-class="is-active"
          class="link"
          :to="{ name: 'authors', params: { } }">
          Authors
        </router-link>
      </div>
      <div v-if="hasUserAuthority">
        <router-link
          active-class="is-active"
          class="link"
          :to="{ name: 'genres', params: { } }">
          Genres
        </router-link>
      </div>
      <div v-if="hasAdminAuthority">
        <router-link
          active-class="is-active"
          class="link"
          :to="{ name: 'users', params: { } }">
          Users
        </router-link>
      </div>
    </nav>
  </header>
</template>
<script>
import store from "../store";

export default {
  name: 'navigation-bar',
  computed: {
    hasUserAuthority() {
      const userAuthorities = store.getters.getCurrentUser.authorities;
      console.log(store.getters.getCurrentUser)
      console.log(store.getters.getCurrentUser.authorities)
      return userAuthorities.includes("user")
    },
    hasAdminAuthority() {
      const userAuthorities = store.getters.getCurrentUser.authorities;
      return userAuthorities.includes("admin")
    }
  }
}
</script>
<style lang="scss">
</style>
