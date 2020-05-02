const URL = {
    blog: '/api/blog'
}


const articleToString = (article) => `
<div class="news_post">
<div class="post_meta">
${article.title}
</div>
<div class="post_image">
<img src="${article.picture}" alt="">
<a href="/articles/${article.title}"><div class="post_image_box text-center">+</div></a>
</div>
<div class="post_text">
<p>${article.content}</p>
</div>
</div>
`

fetch(URL.blog)
    .then(resp => resp.json())
    .then(articles => {
        let result = '';
        articles.forEach(article => {
            result += articleToString(article);
        })
        $('#blog_article').html(result);
    })
