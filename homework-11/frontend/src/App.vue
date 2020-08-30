<template>
  <div id="app">
        <navigation-bar></navigation-bar>
    <!--    <img src="./assets/logo.png">-->
    <router-view></router-view>
  </div>
</template>

<script>
import NavigationBar from "./components/NavigationBar";

export default {
  name: 'app',
  components: {
    NavigationBar: NavigationBar,
  }
}
</script>
