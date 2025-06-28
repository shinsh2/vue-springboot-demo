<template>
  <header class="header">
    <h1 class="logo">
      <span class="blind"></span>
    </h1>
    <p class="header__date">{{ timestamp }}</p>
  </header>
</template>

<script>
import getDate from "../assets/common/getDate.js";
export default {
  data() {
    return {
      timestamp: ""
    };
  },
  created() {
    this.timestamp = `${getDate().month}/${getDate().date} ${getDate().week}`;
  }
};
</script>

<style lang="scss">
.header {
  max-width: $max-width;
  margin: 0 auto;
  padding: 20px 0 55px;
  color: #fff;
  @include flexbox;
  @include align-items(center);
  @include justify-content(space-between);
  &__date {
    letter-spacing: 0.02rem;
    font-size: 1.2rem;
  }
}
.logo {
  width: 5.8rem;
  height: 1.6rem;
  background-size: 100%;
  background-position: 50% 50%;
  background-repeat: no-repeat;
  background-image: icon("logo", "ffffff");
}
</style>