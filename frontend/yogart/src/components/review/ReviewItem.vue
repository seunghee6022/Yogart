<template>
    <div>
        <p>{{ review.rateValue }}</p>
        <p>{{ review.reviewContent }}</p>
        <p>{{ review.userNickname }}</p>
    </div>
</template>

<script>
export default {
    name: 'ReviewItem',
    props: {
        review: Object
    }
}
</script>

<style>

</style>