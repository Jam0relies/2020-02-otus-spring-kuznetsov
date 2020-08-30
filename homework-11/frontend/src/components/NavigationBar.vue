<template>
  <header id="header">
    <nav>
      <div>
        <router-link
          active-class="is-active"
          class="link"
          :to="{ name: 'books', params: { } }">
          Books
        </router-link>
      </div>
      <div>
        <router-link
          active-class="is-active"
          class="link"
          :to="{ name: 'authors', params: { } }">
          Authors
        </router-link>
      </div>
      <div>
        <router-link
          active-class="is-active"
          class="link"
          :to="{ name: 'genres', params: { } }">
          Genres
        </router-link>
      </div>
    </nav>
  </header>
</template>
<script>
export default {
  name: 'navigation-bar',
}
</script>
<style lang="scss">
</style>
