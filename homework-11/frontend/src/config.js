'use strict'
let protocol = location.protocol;
let slashes = protocol.concat("//");
let host = slashes.concat(window.location.hostname);
export let backend = host.concat(":8081")
export default undefined;
