"use strict"

import Vue from "/webjars/vue/2.6.11/vue.min.js"

new Vue({
    el: '#app', // This should be the same as your <div id=""> from earlier.
    components: {
        'navbar': Navbar
    },
    template: MainTemplate
})

