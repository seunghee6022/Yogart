<template>
    <div class="container p-0">
        <div id="title" class="title row"  @click="getImage">
            <div class="col-1 p-0"><i class="fas fa-caret-right fa-2x"></i></div>
            <pre class="col-7 p-0 custom-break-word h6">{{ application.userEmail }}</pre>
            <pre class="col-2 p-0 custom-break-word">{{ application.userName }}</pre>
            <pre class="col-2 pl-0 text-right custom-break-word">{{ application.userNickname }}</pre>
        </div>
        <div class="content">
            <img v-if="isClicked" :src="getImageUrl"/>
            <button v-if="isClicked" @click="permit" class="btn btn-warning ml-5">강사승인</button>
        </div>
    </div>
</template>

<script>
import axios from 'axios'

export default {
    name: 'ApplicationListItem',
    props: {
        application: Object,
    },
    data() {
        return {
            isClicked: false,
            SERVER_URL: this.$store.state.SERVER_URL,
            getImageUrl: this.$store.state.SERVER_URL + '/api/users/registrationImage?userEmail=' + this.application.userEmail
        }
    },
    methods: {
        getImage() {
            this.isClicked = !this.isClicked
        },
        permit() {
            this.$emit('permit', this.application.userEmail)
        }
    }
}
</script>

<style scoped>
img {
    max-height: 500px;
}
.title {
    background-color: rgba(255, 255, 255, 0.7);
    /* border-bottom: 2px solid rgba(215, 159, 215, 1); */
    margin: 0rem;
    padding: 1rem 0rem;
    cursor: pointer;
    transition: all ease 0.5s;

}
.content {
    border-bottom: 2px solid rgba(215, 159, 215, 1);
    border-top: 2px solid rgba(215, 159, 215, 1);
    background-color: rgba(255, 255, 255, 0.7);
}
pre {
    margin: 0.5rem 0rem 0rem;
}
.fa-caret-right {
    color: rgba(215, 159, 215, 1);
}
</style>