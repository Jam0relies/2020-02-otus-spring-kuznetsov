<template>
  <header id="header">
    <nav>
      <div><a href="/books.html">Books</a></div>
      <router-link
        active-class="is-active"
        class="link"
        :to="{ name: 'authors', params: { } }">
        Authors
      </router-link>
      <div><a href="/genres.html">Genres</a></div>
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
